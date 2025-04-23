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
    fileName = "vatId-Cleanup-2nd-List"
    csvFilePath = userHome + downloadFolderName + fileName + ".csv"
    print("csvFilePath: ", csvFilePath)
    # csvFile = open(csvFilePath, "r")
    print()
    # print(csvFile.readline())

    # Json Dialect
    csv.register_dialect('jsonDialect', delimiter=',', quotechar='"', doublequote=True, skipinitialspace=True,
                         lineterminator='\r\n', quoting=csv.QUOTE_MINIMAL)

    ids = []
    jsonResult = []
    # open csv file
    with open(csvFilePath, newline='') as csvFile:
        try:
            csvReader = csv.DictReader(csvFile)
            fieldNames = csvReader.fieldnames
            # displaying the list of column names
            print("fieldNames:", fieldNames)
            for item in list(csvReader):
                csvDict = dict(item)
                # ID, MDM_COMPANY_NAME, CRM_CUSTOMER_ID, VAT_ID, VAT_OPT_OUT
                # {
                #     "id": "1838350",
                #     "vatId": "ESB66041583"
                #     "vatOptOut":false
                # }
                jsonResult.append("{")
                jsonResult.append("\"id\":" + csvDict["ID"])
                jsonResult.append(", \"vatId\":\"\"")
                # jsonResult.append(", \"vatId\":" + csvDict["CRM_CUSTOMER_ID"])
                # jsonResult.append(", \"vatOptOut\":false")
                jsonResult.append("}, ")

                ids.append("'" + csvDict["CRM_CUSTOMER_ID"] + "', ")

            ids.append(")")

        except csv.Error as e:
            sys.exit('file {}, line {}: {}'.format(csvFilePath, csvReader.line_num, e))

    # close csv file
    csvFile.close()

    # open json file
    jsonFilePath = userHome + downloadFolderName + fileName + ".json"
    print("jsonFilePath: ", jsonFilePath)
    write_json(jsonFilePath, "\n", jsonResult)
    print()

    # open json file
    jsonFilePath = userHome + downloadFolderName + fileName + "-ids.json"
    print("jsonFilePath: ", jsonFilePath)
    write_json(jsonFilePath, "", ids)

    print()
