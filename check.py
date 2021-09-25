import pandas as pd
import cleandata

df = cleandata.read_and_cut(r"D:/Projects/Python_Projects/HacksAI/a.csv", [4, 5, 6])
print(df.head())
print(len(df))

