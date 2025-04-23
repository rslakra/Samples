#! /usr/bin/env python

"""
A Python load runner main program.

Author - Rohtash Lakra
"""
class LoadRunner:

    def __init__(self):


if __name__ == ‘__main__‘:
    rows = []
    for i in range(1, 10000):
        for num in range(1, 100):
            dateTimeObj = datetime.now()
            timeStampStr = dateTimeObj.strftime(“ % d - % m - % Y % H: % M: % S”)
            rows.append((table_sequence_name, num, to_date(‘” + str(timeStampStr) + “’, ’DD - MM - YYYY
            HH24: MI:SS’)));
            # insert multiple rows in job_queue
            insert_job_queue(rows)
            rows = []