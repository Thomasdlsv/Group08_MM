import pandas as pd
import geopy.distance

def dist(coords_1,coords_2):
   dist = geopy.distance.geodesic(coords_1, coords_2).km
   print( dist)
   return dist

df = pd.read_csv("AIS_2020_03_03.csv")
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
boat = sailingBoats[sailingBoats['MMSI'] == 338352571]
boat = boat.drop("MMSI",axis =1)
print(boat)
distance = pd.Series(dtype='int')
distance[0] = 0
oldCord = (0,0)
distSum=0
for index, row in enumerate( boat.iterrows()):
    #print(row[1][1])
    if index == 0:
      oldCord=(row[1][1],row[1][2])
    newCord=(row[1][1],row[1][2])
    value = dist(oldCord,newCord)
    distSum+=value 
    distance[index+1]=distance[index] + value
    oldCord=newCord
    
#boat['distance'] = distance 
print("Distance: " +str(distSum))
print(distance)
