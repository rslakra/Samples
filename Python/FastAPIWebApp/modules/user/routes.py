#
# Author: Rohtash Lakra
#
from typing import Annotated, List

from fastapi import APIRouter, Request, Body, status

from modules.user.model import User
from modules.user.service import UserService

# /users router
router = APIRouter(prefix="/users", tags=["User's Management"])
userService = UserService()


@router.post(
    "/",
    status_code=status.HTTP_200_OK,
    response_model=User,
    summary="Create a user",
    description="Creates a new user",
)
async def addUser(
        request: Request,
        payload: Annotated[User, Body()],
):
    print(request)
    # userService.addUser(payload)
    return None


@router.get(
    "/",
    status_code=status.HTTP_200_OK,
    response_model=List[User],
    summary="Get the list of users",
    description="Returns the list of users or filtered users",
)
async def getUsers(
        request: Request,
        # filters: Annotated[Optional[UserFilter], Query()],
):
    print(request)
    # print(filters)
    # return userService.addUser(filters)
    return None


class UserRoutes:
    """User's Controller"""

    def __init__(self):
        pass

    def addUser(self, user: User):
        self.service.addUser(user)

    def getUsers(self):
        """Returns the list of users"""
        return self.service.getUsers()

    def getUser(self, email: str):
        """Returns the user for the email address"""
        return self.service.getUser(email)
