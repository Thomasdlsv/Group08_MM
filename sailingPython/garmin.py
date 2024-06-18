import pandas as pd
import plotly.express as px

from weather import *

df = pd.read_csv("datasets/garmin/bestD.csv")
fields = [
    "GOTOES_CSV",
    "timestamp",
    "position_lat",
    "cadence",
    "right_power_phase_peak",
]

df = df[fields]
df.rename(
    columns={
        "GOTOES_CSV": "BaseDateTime",
        "timestamp": "LAT",
        "position_lat": "LON",
        "cadence": "distance",
        "right_power_phase_peak": "speed",
    },
    inplace=True,
)
df = df.reset_index(drop=True)

# Drop for some
# df = df.drop(df.index[:20])


def fixTime(str):
    str = str.replace(" ", "T")
    return str


df["BaseDateTime"] = df["BaseDateTime"].apply(lambda x: fixTime(x))
print(df)


def plott(boat):
    color_scale = [(0, "orange"), (1, "red")]
    fig = px.scatter_mapbox(
        boat,
        lat="LAT",
        lon="LON",
        hover_name="BaseDateTime",
        hover_data="BaseDateTime",
        color_continuous_scale=color_scale,
        zoom=8,
        height=800,
        width=800,
    )

    fig.update_layout(mapbox_style="open-street-map")
    fig.update_layout(margin={"r": 0, "t": 0, "l": 0, "b": 0})
    fig.show()


def setupWeather(boat):
    lat = boat.iloc[0]["LAT"]
    lon = boat.iloc[0]["LON"]
    date = boat.iloc[0]["BaseDateTime"][0:10]
    returnable = []
    returnable.append(lat)
    returnable.append(lon)
    returnable.append(date)
    return returnable


def addWind(boat):
    sboat = boat
    list = setupWeather(boat)
    weatherCall = Wind(list[0], list[1], list[2])
    sboat["BaseDateTime"] = [pd.Timestamp(x) for x in sboat["BaseDateTime"]]
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


addWind(df)
