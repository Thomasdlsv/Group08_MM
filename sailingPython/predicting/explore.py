import pandas as pd

# Hull type, Category, Hull lenght, Beam (width), Upwind sail area, Downwind sail area, Mainsail area, Mast configuration, Fuel type, Critical hull speed, Overall length,
# Maybe construction, Genoa area,
df = pd.read_excel("datasets/sailing_database.xlsx")
df = df[
    [
        "Engine(s)",
        "Hull type",
        "Category",
        "Hull length",
        "Beam (width)",
        "Upwind sail area",
        "Downwind sail area",
        "Mainsail area",
        "Mast configuration",
        "Critical hull speed",
        "Overall length",
    ]
]
df = df[df["Engine(s)"] == "No engine"]
df = df[df["Hull type"] == "Monohull"]
print(df)
