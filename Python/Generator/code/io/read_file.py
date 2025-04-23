import csv
import os
# Press the green button in the gutter to run the script.
# starting point
import sys


# writes the json list into given file.
def write_json(output_file_path, delimiter, json_list):
    # print(f'write_json({output_file_path}, {json_list})')
    print(f'write_json({output_file_path})')
    # open file to write into
    with open(output_file_path, "w") as file:
        # jsonFile.write(jsonResult)
        file.write(delimiter.join(str(item) for item in json_list))
        # json.dump(jsonResult, jsonFile)

    # close json file
    file.close()


if __name__ == '__main__':
    print()
    userHome = os.getenv("HOME")
    print("userHome: ", userHome)
    downloadFolderName = "/Downloads/"
    csvFileName = "GEMINIAPP-16780 - Patch Advertiser Funds - Sheet2.csv"
    csvFilePath = userHome + downloadFolderName + csvFileName
    print("csvFilePath: ", csvFilePath)
    # csvFile = open(csvFilePath, "r")
    print()
    # print(csvFile.readline())

    # Json Dialect
    csv.register_dialect('jsonDialect', delimiter=',', quotechar='"', doublequote=True, skipinitialspace=True,
                         lineterminator='\r\n', quoting=csv.QUOTE_MINIMAL)

    jsonAdvFundIds = []
    jsonResult = []
    # open csv file
    with open(csvFilePath, newline='') as csvFile:
        try:
            csvReader = csv.DictReader(csvFile)
            # converting the file to dictionary
            # by first converting to list
            # and then converting the list to dict
            # csvDict = dict(list(csvReader)[0])
            # fieldNames = list(csvDict.keys())
            fieldNames = csvReader.fieldnames
            # displaying the list of column names
            print("fieldNames:", fieldNames)
            # {
            #     "advertiserId": 1000948418,
            #     "accountSpendCapType": "UNLIMITED"
            # },

            jsonAdvFundIds.append("WHERE advertiser_id IN (")
            for item in list(csvReader):
                csvDict = dict(item)

                jsonResult.append("{")
                jsonResult.append("\"id\":" + csvDict["ID"])
                jsonResult.append(", \"advertiserId\":" + csvDict["ADVERTISER_ID"])
                jsonResult.append(", \"accountSpendCapType\":\"UNLIMITED\"")
                jsonResult.append("}, ")

                jsonAdvFundIds.append(csvDict["ADVERTISER_ID"] + ", ")

            jsonAdvFundIds.append(")")

        except csv.Error as e:
            sys.exit('file {}, line {}: {}'.format(csvFilePath, csvReader.line_num, e))

    # close csv file
    csvFile.close()

    # open json file
    jsonFilePath = userHome + downloadFolderName + "GEMINIAPP-16780 - Patch Advertiser Funds - Sheet2.json"
    print("jsonFilePath: ", jsonFilePath)
    write_json(jsonFilePath, "\n", jsonResult)
    print()

    # open json file
    jsonFilePath = userHome + downloadFolderName + "GEMINIAPP-16780-Advertiser Funds-IDs.json"
    print("jsonFilePath: ", jsonFilePath)
    write_json(jsonFilePath, "", jsonAdvFundIds)

    # # open csv file
    # with open(csvFilePath, newline='') as csvFile:
    #     csvReader = csv.reader(csvFile, delimiter=',')
    #     # fieldName = csvReader.fieldnames
    #     # extracting field names through first row
    #     fieldNames = next(csvReader)
    #     print("Total Columns: %d" %(len(fieldNames)))
    #     print(fieldNames)
    #
    #     try:
    #         for row in csvReader:
    #             # print(', '.join(row))
    #             # print(row)
    #             # for column in fieldNames:
    #             #     print(row[column])
    #
    #             # print(dict("id", row[0]))
    #             json.append("{")
    #             # columns = len(row)
    #             # for column in range(fieldNames):
    #             # json.append(','.join())
    #             json.append("\"id\":")
    #             json.append(row[0])
    #             json.append(", ")
    #
    #             json.append("}, ")
    #
    #     except csv.Error as e:
    #         sys.exit('file {}, line {}: {}'.format(csvFilePath, csvReader.line_num, e))

    print()
    # print(json)
