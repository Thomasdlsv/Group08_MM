import datetime

import pandas as pd

from sailing import *
from weather import *

boat = Boat()
list = boat.setupWeather()
weatherCall = Wind(list[0], list[1], list[2])
# fiels = [
#     "MMSI",
#     "Date",
#     "Time",
#     "LAT",
#     "LON",
#     "SOG",
#     "COG",
#     "Length",
#     "Width",
#     "WindSpeed",
#     "WindDirection",
# ]

boat.plott()
boat = boat.boatPR()
print(boat.head)
sboat = boat[
    ["MMSI", "BaseDateTime", "LAT", "LON", "SOG", "COG", "Length", "Width", "Draft"]
]
sboat.reset_index(inplace=True)
sboat["BaseDateTime"] = [pd.Timestamp(x) for x in sboat["BaseDateTime"]]
sboat = sboat.drop("index", axis=1)
sboat = sboat.sort_values(by=["BaseDateTime"])
wind = weatherCall.getter()
wind = wind.drop("date", axis=1)
wind = wind.drop("temperature_2m", axis=1)
sboat["Hour"] = sboat["BaseDateTime"].dt.hour
wind["Hour"] = wind.index
windSpeed = []
windDir = []
for index, row in sboat.iterrows():
    windSpeed.append(wind["wind_speed_10m"][row["Hour"]])
    windDir.append(wind["wind_direction_10m"][row["Hour"]])
sboat["wind_speed"] = windSpeed
sboat["wind_direction"] = windDir
sboat = sboat.drop("Hour", axis=1)
sboat.to_csv("datasets/firstBoat.csv")
print(sboat)
