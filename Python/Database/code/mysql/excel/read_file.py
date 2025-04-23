import os

print()
userHome = os.getenv("HOME")
print("userHome: ", userHome)
csvFileName = "TimeZone-Update-2020Aug18.csv"
csvFilePath = userHome + csvFileName
print("csvFilePath: ", csvFilePath)
csvFile = open(csvFilePath, "r")
# print(csvFile.readline())

json = []
for line in csvFile:
    print(line)
    json.append("{")
    columns = line.split()
    for column in range(columns):
        json.append("\"id\":")
        json.append(column)
        json.append(", ")

    json.append("}, ")

csvFile.close()
print(json)
