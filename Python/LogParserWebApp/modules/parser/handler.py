#
# Author: Rohtash Lakra
#
from ...framework.enums.base import BaseEnum


class FileType(BaseEnum):
    """Enum for all file types"""
    CSV = ".csv"
    ICS = ".ics"
    LOG = ".log"
    ZIP = ".zip"


class LogParser:
    """BaseClass of all the log-parsers"""

    def __init__(self, fileType: FileType, logFolder: str, logFileName: str):
        self.fileType = fileType
        self.logFolder = logFolder
        self.logFileName = logFileName

    def __str__(self):
        """Returns the string representation of this object."""
        return f"{self.__class__.__name__} <fileType={self.fileType}, logFolder={self.logFolder}, logFileName={self.logFileName}>"

    def __repr__(self):
        """Returns the string representation of this object."""
        return str(self)

    @property
    def fileType(self):
        return self.fileType

    @property
    def logFolder(self):
        return self.logFolder

    @property
    def logFileName(self):
        return self.logFileName

    def parse(self) -> None:
        """Parses logs"""
        pass


class LogHandler:
    """BaseClass of all the log-handlers """

    def __init__(self, logParser: LogParser):
        self.logParser = logParser

    def __str__(self):
        """Returns the string representation of this object."""
        return f"{self.__class__.__name__} <logParser={self.logParser}>"

    def __repr__(self):
        """Returns the string representation of this object."""
        return str(self)


# init objects
logParser = LogParser(FileType.CSV, "/var/logs", logFileName="logs.csv")
print(f"logParser={logParser}")
logHandler = LogHandler(logParser)
print(f"logHandler={logHandler}")
