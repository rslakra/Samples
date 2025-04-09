#
# Author: Rohtash Lakra
#
from fastapi import APIRouter

from modules.user.routes import router as user_router

# /v2 router
v2_router = APIRouter(prefix="/v2", tags=["v2"])

# register routes
v2_router.include_router(user_router)
