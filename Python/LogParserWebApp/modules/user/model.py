#
# Author: Rohtash Lakra
#
from typing import Optional, List
from uuid import UUID, uuid4

from pydantic import BaseModel

from framework.enums import Gender, Role


class User(BaseModel):
    """User Model"""

    id: Optional[UUID] = uuid4()
    email: str
    first_name: str
    last_name: str
    gender: Gender
    roles: List[Optional[Role]]


class UserFilter:
    id: UUID
    email: str
    first_name: str
    last_name: str
