# coding: utf-8
from __future__ import unicode_literals

import codecs
import os

import xlrd


# start of gen_json_script
def gen_json_script(excel_source_file_path, output_path, sheet_names, table_names):
    print(f'gen_json_script({excel_source_file_path}, {output_path}, {sheet_names}, {table_names})')
    book = xlrd.open_workbook(excel_source_file_path)
    out_file = codecs.open(output_path, "w", "utf_8")

    for i, sheet_name in enumerate(sheet_names):
        table_name = table_names[i]
        sheet = book.sheet_by_name(sheet_name)

        column_list = []
        for col in range(sheet.ncols):
            column_list.append(sheet.cell(0, col).value)

        sql_prefix = "INSERT INTO %s(%s) VALUES(" % (table_name, ",".join(column_list))
        sql_suffix = ");\n"

        for row in range(sheet.nrows):
            if row >= 1:
                value_list = []
                for col in range(sheet.ncols):
                    val = sheet.cell(row, col).value
                    if type(val) != float:
                        val = "'%s'" % (val,)
                    else:
                        val = str(val).replace(".0", "")
                    value_list.append(val)
                sql = "%s%s%s" % (sql_prefix, ",".join(value_list), sql_suffix,)
                out_file.write(sql)

    # end of outer loop
    out_file.close()


# Press the green button in the gutter to run the script.
# starting point
if __name__ == '__main__':
    print()
    userHome = os.getenv("HOME")
    print("userHome: ", userHome)
    downloadFolderName = "/Downloads/"
    csvFileName = "UCAM-TimeZone-Update-WithAds-2020Aug18.csv"
    csvFilePath = userHome + downloadFolderName +  csvFileName
    print("csvFilePath: ", csvFilePath)
    targetFilePath = userHome + downloadFolderName + "jsonTimeZone.json"
    print("targetFilePath: ", targetFilePath)
    gen_json_script(csvFilePath, targetFilePath, "UCAM-TimeZone-Update-WithAds-20", "table_names")
