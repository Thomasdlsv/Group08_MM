import geopy.distance
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd


def dist(coords_1, coords_2):
    dist = geopy.distance.geodesic(coords_1, coords_2).km
    return dist


df = pd.read_csv("datasets/AIS_2020_03_03.csv")
df = df.drop("VesselName", axis=1)
df = df.drop("CallSign", axis=1)
df = df.drop("TransceiverClass", axis=1)
sailingBoats = df[df["VesselType"] == 36]
sailingBoats = sailingBoats.drop("VesselType", axis=1)
sailingBoats = sailingBoats.drop("Cargo", axis=1)
sailingBoats = sailingBoats.drop("Draft", axis=1)
sailingBoats = sailingBoats.drop("Width", axis=1)
sailingBoats = sailingBoats.drop("Length", axis=1)
sailingBoats = sailingBoats.drop("Status", axis=1)
sailingBoats = sailingBoats.drop("Heading", axis=1)
sailingBoats = sailingBoats.drop("IMO", axis=1)
sailingBoats = sailingBoats[sailingBoats["SOG"] > 0]
boat = sailingBoats[sailingBoats["MMSI"] == 338352571]
boat = boat.drop("MMSI", axis=1)
print(boat)

distance = pd.Series(dtype="int")
distance[0] = 0
oldCord = (0, 0)
distSum = 0
index = 0

for _, row in boat.iterrows():
    newCord = (row["LAT"], row["LON"])
    if index == 0:
        oldCord = newCord
        distance[index] = 0
        index += 1
    else:
        value = dist(oldCord, newCord)
        distSum += value
        distance[index] = distSum + value
        oldCord = newCord
        index += 1

boat["distance"] = distance
print("Distance: " + str(distSum))
print(distance)
data = distance
indices = np.arange(len(data))

# Plot the data against the indices
plt.plot(indices, data)
plt.xlabel("Indices")
plt.ylabel("Values")
plt.title("Plot of 1D Array")

# Display the plot
plt.show()
