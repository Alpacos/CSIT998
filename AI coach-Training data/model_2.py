'''
model 2:
repeat after the initial users
'''
import tensorflow as tf
import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import os

import os
for dirname, _, filenames in os.walk('.'):
    for filename in filenames:
        print(os.path.join(dirname, filename))
# Matplotlib and seaborn for visualization
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.metrics import mean_absolute_error,r2_score
from sklearn.model_selection import train_test_split
from sklearn import linear_model
from sklearn.preprocessing import PolynomialFeatures

# Import Calories Dataset
df_cal = pd.read_csv(os.path.join(dirname,'calories.csv'))

# Import Exercises Dataset
df_ex  = pd.read_csv(os.path.join(dirname,'exercise.csv'))

# Merging Datasets
df = pd.merge(df_ex, df_cal, on = 'User_ID')

# Get dummies
df['Gender'] = pd.get_dummies(df['Gender'], prefix_sep='_', drop_first=True)

def bmi_cal(weight, height):
    w = weight
    h = height/100
    bmi = w/(h*h)
    return round(bmi,2)
df['bmi'] = df.apply(lambda x: bmi_cal(x['Weight'],x['Height']),axis=1)
#df.head()

#print correlation of variables and calories
'''
correlations = df.drop(columns=['User_ID'],axis=1).copy().corr()['Calories']
correlations = correlations.sort_values(ascending=False).drop('Calories',axis=0)
print(correlations)
correlations.to_frame().plot.bar();
'''
df_3f = df[['Duration','Heart_Rate','Body_Temp','Calories']].copy()
df_3f.rename(columns={'Duration':'t',
                      'Heart_Rate':'h',
                      'Body_Temp':'T',
                      'Calories':'C'},inplace=True)
# sns.pairplot(df_3f,markers=["o", "s", "D"])

THT_i = df_3f[['t','h','T']].to_numpy()
C_i = df_3f['C'].to_numpy().reshape(-1,1)
THT_trn, THT_tst, C_trn, C_tst = train_test_split( THT_i,C_i, test_size=0.2, random_state=42)

# Create regression object
MQ3 = linear_model.LinearRegression()

MQ3_poly = PolynomialFeatures(degree=2)
THT_trn_pl = MQ3_poly.fit_transform(THT_trn)
THT_tst_pl = MQ3_poly.fit_transform(THT_tst)

# Train the model using the training sets
MQ3.fit(THT_trn_pl,C_trn)

C_mq3_tst = MQ3.predict(THT_tst_pl)
C_mq3_trn = MQ3.predict(THT_trn_pl)

MAE_tst= mean_absolute_error(C_tst,C_mq3_tst)
r2_score_tst= r2_score(C_tst,C_mq3_tst)

MAE_trn= mean_absolute_error(C_trn,C_mq3_trn)
r2_score_tsn= r2_score(C_trn,C_mq3_trn)

# print('Mean error (test): ',MAE_tst)
# print('R2 (test):    ',r2_score_tst)

# print('\nMean error (train): ',MAE_trn)
# print('R2 (train):  ',r2_score_tsn)

model = MQ3
