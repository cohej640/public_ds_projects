import numpy as np
from numpy import genfromtxt
import pandas as pd
from sklearn.svm import SVC
from sklearn.preprocessing import LabelEncoder
import csv
from sklearn.model_selection import train_test_split

default_path = 'C:/Users/John/Downloads/'
#my_data = genfromtxt(default_path + 'test.csv', delimiter =',')

#dataFrame = pd.DataFrame(my_data, columns = (my_data.colu))

#my_data2 = pd.read_csv(default_path + 'tejst.csv')


#the percs of a SVM are that they are robust to outliers !!
# it can maximize margin by minimizing ||w||2

# it also has the benefit of being a convex optimization problem

# maybe we want to allow for a soft-margin, SVM allowing for some slack penalty

"""
The goal of an SVM for linearly separable binary sets
is to design a hyperplane that classifies all training vectors
into two classes. 

The best hyperplane is the one that leaves the maximum margin from both classes

A weight vector multiplied by x + omega 0 that will deliver a g(x) >= 1 for all of those
that belong to class 1 and <= -1 for all of those that belong to class 2

Minimizing the weight vector will give us the largest margin!!

To minimize this weight vector is a nonlinear optimization task using LaGrange Multipliers

The weighting is known as the support value. 

"""

# SVC takes as input two arrays, an array X of size [n_samples, n_features] holding
# The training samples and an array y of class labels(strings an int) size n_sample
#df = pd.read_csv(default_path + 'test.csv')


# Our target will be 0 or 1, and it is the first column called class

# we will want to use all of the features!!

#X = df.drop(['class'], axis = "columns")
#Y = df['class']
#X_train, X_test, y_train, y_test = train_test_split(X,Y,test_size = 0.0)
#print(len(X)) # this should be the size of the sample
#df = df.astype(dtype = 'float64', copy = True, errors = 'ignore')
#df = pd.read_csv(default_path + 'train.csv').values
#f = np.vectorize(lambda x: ord(x) - ord('a')+1)
#my_data = f(df)

df = pd.read_csv(default_path + 'train.csv')
test_df = pd.read_csv(default_path + 'test.csv')

label = LabelEncoder()
df = df.drop(['Id'], axis = "columns")
#print("got here")
for col in df.columns:
    df[col] = label.fit_transform(df[col])

label = LabelEncoder()
new_test_df = test_df.drop(['Id'], axis = "columns")
for col in new_test_df.columns:
    new_test_df[col] = label.fit_transform(new_test_df[col])


X1 = new_test_df

#print("i got here")
#X_train, X_test, Y_train, Y_test = train_test_split(X,Y,test_size = 0.2)

#testing =

#print(model.score(X_test, Y_test))


X = df.drop(['class'], axis = "columns")
Y = df['class']

model = SVC()
model.fit(X,Y)
#print(model.predict(X))
# id, class
ids = test_df['Id']
target_names = ["e", "p"]
#print('i got here')
predictions = model.predict(X1)
#print('but i did not get here ')
predictDf = pd.DataFrame(predictions)

submission  = pd.DataFrame({'Id': ids, 'thing': predictions})
final = pd.DataFrame({'Id':ids})
final['class'] = submission.thing.apply(lambda x: target_names[x])


with open(default_path + 'output.csv', 'w', newline="") as writeFile:
    writer = csv.writer(writeFile)
    writer.writerow(['class', 'Id'])
    for r in range(0, final['Id'].size):
        # print(final['Id'].values[r])
        # print(final['class'].values[r])
        writer.writerow([final['class'].values[r], final['Id'].values[r]])

#this is the final version of my project!
