#
# Author: Rohtash Lakra
#
import asyncio
import time
from typing import Optional, List

from framework.db.engine import DatabaseEngine
from modules.user.model import User


class UserService:
    """User's Service"""

    def __init__(self):
        self.repository = None
        self.users = List[Optional[User]]

    def addUser(self, user: User):
        if not self.users:
            self.users = List[Optional[User]]

        for tUser in self.users:
            if tUser.email == user.email:
                self.users.remove(tUser)
                self.users.append(user)
            else:
                self.users.append(user)

    def getUsers(self):
        """Returns the list of users"""
        return self.users

    def getUser(self, email: str):
        """Returns the user for the email address"""
        for user in self.users:
            if user.email == email:
                return user

        return None

    def register(self, payload):
        # self.logger.info('register started %s' % payload)
        self.db.execute(...)
        time.sleep(1)  # we simulate I/O wait here
        # self.logger.info('register completed for %s' % payload)


class AsyncUserService:
    """Async User Service"""

    def __init__(self, db_engine: DatabaseEngine):
        self.db = db_engine

    async def register(self, payload):
        # self.logger.info('register started %s' % payload)
        self.db.execute(...)
        await asyncio.sleep(1)  # we simulate I/O wait here
        # self.logger.info('register completed for %s' % payload)
