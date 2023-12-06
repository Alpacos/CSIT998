'''
model 1:
initial the users
'''

import tensorflow as tf
import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import os

import os
for dirname, _, filenames in os.walk('.'):
    for filename in filenames:
        print(os.path.join(dirname, filename))

from sklearn.metrics import mean_absolute_error,r2_score
from sklearn.model_selection import train_test_split
from sklearn import linear_model
from sklearn.model_selection import train_test_split
from xgboost import XGBRegressor
from sklearn import metrics

# Import Calories Dataset
df_cal = pd.read_csv('calories.csv')

# Import Exercises Dataset
df_ex  = pd.read_csv('exercise.csv')

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


calories_data = pd.concat([df_ex, df_cal['Calories']], axis=1)
calories_data.replace({"Gender":{'male':0,'female':1}}, inplace=True)


X = calories_data.drop(columns=['User_ID','Calories'], axis=1)
Y = calories_data['Calories']
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.25, random_state=2)

model = XGBRegressor(n_estimators=1000, learning_rate=0.05, n_jobs=4)
model.fit(X_train, Y_train)

test_data_prediction = model.predict(X_test)
mae = metrics.mean_absolute_error(Y_test, test_data_prediction)
print("Mean Absolute Error = ", mae)


'''transfer the model into tflite file save embedding into android app'''
model.save_model('model_1.model')
import onnxmltools
from xgboost import XGBRegressor
from skl2onnx.common.data_types import FloatTensorType
import onnx
from onnx_tf.backend import prepare
import tensorflow as tf
import tf2onnx

# Load your trained model
model = XGBRegressor()
model.load_model('model_1.model')

# Convert from XGBoost model to ONNX
initial_type = [('float_input', FloatTensorType([None, X_train.shape[1]]))]
onnx_model = onnxmltools.convert_xgboost(model, initial_types=initial_type)

# Save the ONNX model
onnxmltools.utils.save_model(onnx_model, 'model_1.onnx')

# Load the ONNX model
onnx_model = onnx.load("model_1.onnx")
tf_rep = prepare(onnx_model)

tf_rep.export_graph("model_1.pb")
