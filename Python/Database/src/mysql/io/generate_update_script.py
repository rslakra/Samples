# coding: utf-8

from __future__ import unicode_literals

import codecs

import xlrd


def generate_update_sql_script(excel_path, output_path, sheet_names, table_names):
    book = xlrd.open_workbook(excel_path)
    out_f = codecs.open(output_path, "w", "utf_8")

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
                out_f.write(sql)
    out_f.close()
