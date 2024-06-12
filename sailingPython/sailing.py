from random import randint

import pandas as pd
import plotly.express as px


class Boat:

    def __init__(self):
        df = pd.read_csv("datasets/AIS_2020_03_03.csv")
        print(df.columns)
        df = df.drop("VesselName", axis=1)
        df = df.drop("CallSign", axis=1)
        df = df.drop("TransceiverClass", axis=1)

        # MMSI, BaseDateTime, LAT, LON, SOG, COG, Heading, IMO,VesselType, Status, Length, Width, Draft, Cargo
        # website = "https://www.vesselfinder.com/vessels/details/"
        sailingBoats = df[df["VesselType"] == 36]
        sailingBoats = sailingBoats[sailingBoats["SOG"] > 0]

        list_of_Boats = self.create_list_boats(sailingBoats)
        random_boat = randint(0, len(list_of_Boats))
        random_boat = list_of_Boats[random_boat]
        random_boat = 338352571
        self.boat = df[df["MMSI"] == random_boat]
        print("MMSI is:" + str(self.boat.iloc[0]["MMSI"]))

    def plott(self):
        color_scale = [(0, "orange"), (1, "red")]
        fig = px.scatter_mapbox(
            self.boat,
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

    def setupWeather(self):
        lat = self.boat.iloc[0]["LAT"]
        lon = self.boat.iloc[0]["LON"]
        date = self.boat.iloc[0]["BaseDateTime"][0:10]
        returnable = []
        returnable.append(lat)
        returnable.append(lon)
        returnable.append(date)
        return returnable

    def create_list_boats(self, sailingBoats):
        sailingBoat = sailingBoats.drop_duplicates(subset=["MMSI"], keep="first")
        listOfBoats = []
        for boat in sailingBoat["MMSI"]:
            listOfBoats.append(boat)
        return listOfBoats

    def boatPR(self):
        return self.boat
