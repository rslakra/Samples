#
# Author: Rohtash Lakra
#
from enum import unique

from .base import BaseEnum


@unique
class Role(BaseEnum):
    """Gender Enum"""

    ADMIN = "Admin"
    USER = "User"
    READ_ONLY = "ReadOnly"
    GUEST = "Guest"
