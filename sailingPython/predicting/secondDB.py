import re

import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error, r2_score

df = pd.read_excel("datasets/sailing_database_2.xlsx")
df = df[
    [
        "LWL:",
        "Beam:",
        "Draft (max):",
        "S.A. (reported):",
        # "LOA:",
        # "Displacement:",
        # "Ballast:",
        # "S.A. Main:",
    ]
]
print(type(df["Beam:"][0]))
print((df.iloc[0]))


def cleaner(string):
    if isinstance(string, float):
        return string
    cleaned = ""
    setPosition = 100000
    for i, char in enumerate(string):
        if i + 1 > setPosition:
            cleaned = cleaned + char
        if char == "/":
            setPosition = i
    cleaned = cleaned[1:]
    cleaned = cleaned[: len(cleaned) - 2]
    cleaned = re.sub(r",", "", cleaned)
    return float(cleaned)


df = df.apply(lambda x: x.apply(cleaner), axis=1)
rows_with_nan_or_empty = df.isnull().any(axis=1) | df.isna().any(axis=1)
df = df[~rows_with_nan_or_empty]
X = df[["LWL:", "Beam:"]]
y = df["S.A. (reported):"]  # Target variable
regressor = RandomForestRegressor(n_estimators=10, random_state=0, oob_score=True)
regressor.fit(X, y)
oob_score = regressor.oob_score_
print(f"Out-of-Bag Score: {oob_score}")
predictions = regressor.predict(X)
mse = mean_squared_error(y, predictions)
print(f"Mean Squared Error: {mse}")
r2 = r2_score(y, predictions)
print(f"R-squared: {r2}")


list = np.array([[11.45, 4.18]])
list = np.array(list)

print(list)
print(regressor.predict(list))
