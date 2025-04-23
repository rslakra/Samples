#! /usr/bin/env python

"""
A Python load test for job_queue table.

Author - Rohtash Lakra <work.lakra@gmail.com>
"""

from datetime import datetime

# import jwt

DB_ENV_TYPE = "dbEnvType"
DB_HOST = "dbHost"
DB_PORT = "dbPort"
DB_NAME = "dbName"
DB_USER_NAME = "dbUserName"
DB_PASSWORD = "dbPassword"
DB_TABLE = "dbTable"
DB_TABLE_SEQ_GENERATOR = "dbTableSequenceGenerator"
POOL_SIZE = "poolSize"
WAIT_IN_MINUTES = "waitInMinutes"
BATCH__SIZE = "batchSize"

config = {
    DB_ENV_TYPE: "CI",
    DB_HOST: "localhost",
    DB_PORT: "61621",
    DB_NAME: "apps_necb01c",
    DB_USER_NAME: "mb",
    DB_PASSWORD: "mb",
    DB_TABLE: "JOB_QUEUE_RLAKRA",
    DB_TABLE_SEQ_GENERATOR: "sync_jobs_ID_SEQ.nextval",
    POOL_SIZE: "25",
    WAIT_IN_MINUTES: "15",
    BATCH__SIZE: "2000"
}
# print(config)

jsonParameters = '{"rxOn":false, "dr":false, "rmx":false, "cpp":false, "mq":true, "editorialReview":false}'
# print(jsonParameters)

dateString = "to_date(':2', 'DD-MM-YYYY HH24:MI:SS')"
# print(dateStr)

#sql = (
#    f"INSERT INTO mb.{config[DB_TABLE]} (ID, JOB_TYPE, ENTITY_ID, CREATED_ON, STATUS, ADVERTISER_ID, PARAMETERS, MQ_STATUS) VALUES (mb.{config[DB_TABLE_SEQ_GENERATOR]}, 'PYTHON_LOAD_TEST', :1, {dateStr}, 'SYNC_PENDING', 0, '{jsonParameters}', 'SYNC_PENDING')")
#print(sql)


# inserts the records
def createJWTToken(data):
    # construct an insert statement that add a new row to the billing_headers table
    # try:
    #     host = f"'{config[DB_HOST]}:{config[DB_PORT]}/{config[DB_NAME]}'"
    #     print(host)
    #     # establish a new connection
    #     with cx_Oracle.connect(config[DB_USER_NAME], config[DB_PASSWORD], host) as connection:
    #         # create a cursor
    #         with connection.cursor() as cursor:
    #             # execute the insert statement
    #             cursor.executemany(sql, records)
    #             # commit work
    #             connection.commit()
    # except Error as error:
    #     print('Error!!! :')
    #     print(error)


# Main Method.
if __name__ == '__main__':
    records = []
    for i in range(1, 2):
        for num in range(1, 2):
            nowObj = datetime.now()
            timeStampStr = nowObj.strftime("%d-%m-%Y %H:%M:%S")
            records.append((num, str(timeStampStr)));
        # insert multiple rows into table
        createJWTToken(records)
        # reset records
        records = []
