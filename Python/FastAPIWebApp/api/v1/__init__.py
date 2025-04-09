#
# Author: Rohtash Lakra
#
from fastapi import APIRouter

from modules.user.routes import router as user_router

# /v1 router
v1_router = APIRouter(prefix="/v1", tags=["v1"])

# register routes
v1_router.include_router(user_router)
