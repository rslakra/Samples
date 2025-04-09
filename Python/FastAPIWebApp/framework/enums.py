#
# Author: Rohtash Lakra
#
from enum import Enum, unique


@unique
class Gender(str, Enum):
    """Gender Enum"""

    MALE = "Male"
    FEMALE = "Female"
    OTHER = "Other"


@unique
class Role(str, Enum):
    """Gender Enum"""

    ADMIN = "Admin"
    USER = "User"
    READ_ONLY = "ReadOnly"
    GUEST = "Guest"
