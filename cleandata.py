#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import datetime
import numpy as np
import math
df = pd.read_csv(r'C:\a.csv')
index = df[df['velocity'] == 'None'].index
df = df.drop(index)

#df['is stay'] = np.zeros(df.shape[0])
#df['dist_table'] = np.array([0]*df.shape[0])
#df['dist_vel'] = np.array([0]*df.shape[0])
#df['dist_table'].apply(float)
#df['dist_vel'].apply(float)
#df['velocity'] = df['velocity'].apply(float)
#df['time'] = pd.to_datetime(df['time'])


# In[2]:


df_cut = pd.DataFrame(columns=['ship', 'record', 'time', 'latitude', 'longitude', 'course', 'velocity', 'sure_tral'])

df_cut = df.loc[0:2500]

index = df_cut[df_cut['velocity'] == 'None'].index
df_cut = df_cut.drop(index)

df_cut['dist_table'] = np.array([0]*df_cut.shape[0])
df_cut['dist_vel'] = np.array([0]*df_cut.shape[0])
df_cut['dist_table'].apply(float)
df_cut['dist_vel'].apply(float)
df_cut['velocity'] = df_cut['velocity'].apply(float)
df_cut['time'] = pd.to_datetime(df_cut['time'])
df_cut['is stay'] = np.zeros(df_cut.shape[0])


# In[3]:


#Distance between meridians in km = 111,1 * long * cos(lat)
#Distance between parallels = 111,1


# In[4]:


lat = 0
long = 0
def measure_distance_from_table(df, i, lat, long):
    lat += df['latitude'].iloc[i]
    long += df['longitude'].iloc[i]
    dist_lat = 111.1 * (df['latitude'].iloc[i] - df['latitude'].iloc[i - 1]) * long * math.cos(lat)
    dist_long = 111.1 *(df['longitude'].iloc[i] - df['longitude'].iloc[i - 1])
    return (dist_lat ** 2 + dist_long**2)**0.5, lat, long

def measure_distance_from_velocity(df, i):
    delta_time = (df['time'].iloc[i] - df['time'].iloc[i-1]).seconds / 3600
    return 1.852 * df['velocity'].iloc[i] * delta_time


# In[5]:


for i in range(1, df_cut.shape[0]):
    res = list(measure_distance_from_table(df_cut, i, lat, long))
    df_cut['dist_table'].iloc[i] = res[0]
    df_cut['dist_vel'].iloc[i] = measure_distance_from_velocity(df_cut, i)
    lat = res[1]
    long = res[2]


# In[6]:


def time_delta(df, i):
    # return interval in seconds
    return (df['time'].iloc[i]- df['time'].iloc[i - 1]).seconds


# In[7]:


def fix_data(df):
    index1 = []
    for i in range(1, df.shape[0]):
        if df['velocity'].iloc[i] == 0:
            av_dist = 1.852 * time_delta(df, i) / 3600
            if df['dist_table'].iloc[i] >= 10 * av_dist:
                index1.append(i)
            else:
                df['velocity'].iloc[i] = df['dist_table'].iloc[i] / time_delta(df, i) * 3600 
        elif df['dist_table'].iloc[i] / df['dist_vel'].iloc[i] >= 2:
                record = df['record'].iloc[i]
                index1 += list(df[df['record'] == record].index)
    index1 = list(set(index1))
    df=df.drop(index=index1)
    return df
fix_data(df_cut)
print(df_cut.shape)


# In[8]:


def check_daily_staying(df):
    coordinates = []
    distances = []
    ship_count = df['ship'].max()
    for k in range(1,ship_count):
        record_count = df[df['ship'] == k]['record'].max()
        for i in range(1,record_count):
            summary_lat = df[(df['ship'] == k) & (df['record'] == i)]['latitude'].sum()*111.11
            summary_long = df[(df['ship'] == k) & (df['record'] == i)]['longitude'].sum()*111.11
            
            distance = (summary_lat**2 + summary_long**2)**0.5
            
            if distance >= 1.5:
                df['is stay'][(df_cut['ship'] == k) & (df['record'] == i)] = False
            else:
                df['is stay'][(df_cut['ship'] == k) & (df['record'] == i)] = True


# In[9]:


check_daily_staying(df_cut)


# In[12]:


import matplotlib.pyplot as plt

count_stay_tral = 0
count_not_stay_tral = 0
count_not_stay_not_tral = 0
count_stay_not_tral = 0


def show_hist(start,fin, df_cut):
    for i in range(start, fin+1):
        count_stay_tral = df_cut[df_cut['ship'] == i][df_cut['is stay'] == True][df_cut['sure_tral'] == 1].shape[0]
        count_not_stay_tral = df_cut[df_cut['ship'] == i][df_cut['is stay'] == False][df_cut['sure_tral'] == 1].shape[0]
        count_not_stay_not_tral = df_cut[df_cut['ship'] == i][df_cut['is stay'] == False][df_cut['sure_tral'] == 0].shape[0]
        count_stay_not_tral = df_cut[df_cut['ship'] == i][df_cut['is stay'] == True][df_cut['sure_tral'] == 0].shape[0]
        x = np.array(['stay_tral', 'not_stay_tral', 'not_stay_not_tral', 'stay_not_tral'])
        y = np.array([count_stay_tral, count_not_stay_tral, count_not_stay_not_tral, count_stay_not_tral])
        print('Mean dailty velocity not_stay tral is ', df_cut[df_cut['ship'] == i][df_cut['is stay'] == False][df_cut['sure_tral'] == 1]['velocity'].mean())
        print('Mean dailty velocity not_stay not_tral is ', df_cut[df_cut['ship'] == i][df_cut['is stay'] == False][df_cut['sure_tral'] == 0]['velocity'].mean())
        print('Median mean dailty velocity not_stay tral is ', df_cut[df_cut['ship'] == i][df_cut['is stay'] == False][df_cut['sure_tral'] == 1]['velocity'].median())
        print('Median mean dailty velocity not_stay not_tral is ', df_cut[df_cut['ship'] == i][df_cut['is stay'] == False][df_cut['sure_tral'] == 0]['velocity'].median())
        plt.bar(x, y)
        plt.title(f'{i} ship')
        plt.show()
    
show_hist(1,3, df_cut)


# In[11]:




