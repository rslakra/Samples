#
# Author: Rohtash Lakra
#
from enum import unique

from .base import BaseEnum


@unique
class Gender(BaseEnum):
    """Gender Enum"""

    MALE = "Male"
    FEMALE = "Female"
    OTHER = "Other"
