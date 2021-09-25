import pandas as pd
import cleandata

df = cleandata.read_and_cut(r"D:/Projects/Python_Projects/HacksAI/a.csv")
print(df.tail())
