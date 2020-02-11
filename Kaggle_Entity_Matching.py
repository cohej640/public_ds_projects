import pandas as pd
from fuzzywuzzy import fuzz
from fuzzywuzzy import process
import numpy
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from numpy import double
from sklearn.svm import SVC
from sklearn.preprocessing import LabelEncoder
import csv

default_path = r'C:/Users/John/Documents/SpringSem2019/cs639/PA5/'
default_path2 = r'C:/Users/John/Documents/SpringSem2019/cs639/PA5/Try NUmber 2/'
train_df = pd.read_csv('./train.csv')
test_df = pd.read_csv('./test.csv')
ltable_df = pd.read_csv(default_path2 + 'ltable.csv', encoding = "ISO-8859-1")
rtable_df = pd.read_csv('./rtable.csv', encoding = "ISO-8859-1")
#itable_df = pd.read_csv('./Itable.csv')
#itable_df = pd.read_csv(default_path + 'Itable.csv')
#rtable_df = pd.read_csv(default_path + 'rtable.csv')

def get_matches(query, choices, limit =3):
    results = process.extract(query, choices, limit = limit)
    return results


"""
We can use the FuzzyWuzzy package when the lev distance falls short
from fuzzywuzzy import fuzz
Str1 = "Apple Inc."
Str2 = "apple Inc"
Ratio = fuzz.ratio(Str1.lower(),Str2.lower())
print(Ratio)

the partial ratio of fuzzywuzzt is capable of detecting that both strings
are refering to the lakers
-- thus yielding a 100% similarity


the fuzz.token functions have an important advantage over the ratio and the partial
ratio. They tokenize strings and preprocess them by turning them to lower case
and getting rid of the puncuation.

set ratio takes an even more flexible appproach than set ratio


you can also import process that will allow you to calculate the string
with the highest similarity out of a vector of strings

"""

#maybe make a linear vector of weights for each value.. how do we set the cutoff

lTitles = ltable_df['title']
rTitles = rtable_df['title']

#my_train_df = pd.DataFrame(columns = ['titleSim', 'categorySim', 'brandSim',
                        #              'modelSim', 'priceDiff', 'label'])
my_train_df = pd.DataFrame(columns= ['titleSim', 'categorySim', 'brandSim',
                                      'modelSim', 'priceDiff', 'label'])

totalSumPrice = 0
for r in range (0, train_df['ltable_id'].size):
    # We will now need to get the ltableid features
    indexL = train_df['ltable_id'].values[r]
    ltitle = ltable_df['title'].values[indexL]
    indexR = train_df['rtable_id'].values[r]
    rtitle = rtable_df['title'].values[indexR]
    titleSim = fuzz.partial_token_sort_ratio(rtitle, ltitle) # This is the titles' similarity

    indexCL = train_df['ltable_id'].values[r]
    lCategory = ltable_df['category'].values[indexCL]
    indexCR = train_df['rtable_id'].values[r]
    rCategory = rtable_df['category'].values[indexCR]
    categorySim = fuzz.partial_token_sort_ratio(lCategory, rCategory)

    indexBL = train_df['ltable_id'].values[r]
    lBrand = ltable_df['brand'].values[indexBL]
    indexBR = train_df['rtable_id'].values[r]
    rBrand = rtable_df['brand'].values[indexBR]
    brandSim = fuzz.partial_token_sort_ratio(lBrand, rBrand)

    lModelNo = ltable_df['modelno'].values[indexCL]
    rModelNo = rtable_df['modelno'].values[indexCR]
    modelSim = fuzz.partial_token_sort_ratio(lModelNo, rModelNo)

    lPrice = ltable_df['price'].values[indexCL]
    rPrice = rtable_df['price'].values[indexCR]

    rollingAvg = float(totalSumPrice) / float(r+1)

    priceDiff = abs(lPrice - rPrice)
    if (numpy.isnan(priceDiff)):
        priceDiff = rollingAvg
        # this might not be good enough

    totalSumPrice += priceDiff
    label = train_df['label'].values[r]
    my_train_df.loc[r] = [titleSim, categorySim, brandSim, modelSim, priceDiff, label]
    """
    'titleSim', 'categorySim', 'brandSim',
    'modelSim', 'priceDiff', 'label'
    """
    # tempDic = {'titleSim': titleSim, 'categorySim': categorySim, 'brandSim': brandSim,
    # 'modelSim': modelSim, 'priceDiff': priceDiff, 'label': label}
    #
    # my_train_df.append(tempDic, ignore_index=True)


X = my_train_df.drop(['label'], axis = "columns")
Y = my_train_df['label']

model = SVC()
model.fit(X,Y)

my_test_df = pd.DataFrame(columns= ['titleSim', 'categorySim', 'brandSim',
                                      'modelSim', 'priceDiff'])
for r in range (0, test_df['ltable_id'].size):
    # We will now need to get the ltableid features
    indexL = test_df['ltable_id'].values[r]
    ltitle = ltable_df['title'].values[indexL]
    indexR = test_df['rtable_id'].values[r]
    rtitle = rtable_df['title'].values[indexR]
    titleSim = fuzz.partial_token_sort_ratio(rtitle, ltitle) # This is the titles' similarity

    indexCL = test_df['ltable_id'].values[r]
    lCategory = ltable_df['category'].values[indexCL]
    indexCR = test_df['rtable_id'].values[r]
    rCategory = rtable_df['category'].values[indexCR]
    categorySim = fuzz.partial_token_sort_ratio(lCategory, rCategory)

    indexBL = test_df['ltable_id'].values[r]
    lBrand = ltable_df['brand'].values[indexBL]
    indexBR = test_df['rtable_id'].values[r]
    rBrand = rtable_df['brand'].values[indexBR]
    brandSim = fuzz.partial_token_sort_ratio(lBrand, rBrand)

    lModelNo = ltable_df['modelno'].values[indexCL]
    rModelNo = rtable_df['modelno'].values[indexCR]
    modelSim = fuzz.partial_token_sort_ratio(lModelNo, rModelNo)

    lPrice = ltable_df['price'].values[indexCL]
    rPrice = rtable_df['price'].values[indexCR]

    rollingAvg = float(totalSumPrice) / float(r+1)

    priceDiff = abs(lPrice - rPrice)
    if (numpy.isnan(priceDiff)):
        priceDiff = rollingAvg
        # this might not be good enough

    totalSumPrice += priceDiff
    my_test_df.loc[r] = [titleSim, categorySim, brandSim, modelSim, priceDiff]

predictions = model.predict(my_test_df)
predictDf = pd.DataFrame(predictions)

for r in range(0, predictDf.size):
    int(predictDf.values[r])

with open(default_path + 'output.csv', 'w', newline="") as writeFile:
    writer = csv.writer(writeFile)
    writer.writerow(['id', 'label'])
    for r in range(0, predictDf.size):
        # print(final['Id'].values[r])
        # print(final['class'].values[r])
        writer.writerow([test_df['id'].values[r], int(predictDf.values[r])])

#
# X = my_train_df.drop(['label'], axis = "columns")
# Y = my_train_df['label']
# X_train, X_test, Y_train, Y_test = train_test_split(X,Y,test_size = 0.2)
#
# model = SVC()
# model.fit(X_train, Y_train)

"""

my_train_df:

titleSim | categorySim | brandSim | modelnoSim | diffPrice | train_df['label'].values[r]

.25 +  = .89 --> .45 > not a match

"""






"""
How about we use the difference, or ratios as the features and the label
as the true value !! YES !

"""
#what if we were to train with the difference of the features as the features


#
# index = 0
# for rtitle in rTitles:
#     ltitle = lTitles.get(index)
#     titleRatio = fuzz.token_sort_ratio(rtitle, ltitle)
#     print(titleRatio)
#     index += 1

# pairs = {}
# index = 0
# for left in lTitles:
#     right = rTitles.get(index)
#     pairs[left+"," + right] = fuzz.token_set_ratio(left, right)
#     index += 1
#

#we would need to reverse this! to be the rtitles!
#now we have the ratio for each pair in the set

# THIS IS THE FINAL VERSION OF MY CODE!!!