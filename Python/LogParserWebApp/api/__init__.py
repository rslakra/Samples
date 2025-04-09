#
# Author: Rohtash Lakra
#
from fastapi import APIRouter, Depends

from framework.security import validate_headers
from .v1 import v1_router
from .v2 import v2_router

# /api router
api_router = APIRouter(prefix="/api", tags=["api"], dependencies=[Depends(validate_headers)])

# register routes
# api_router.include_router(user_router)
api_router.include_router(v1_router)
api_router.include_router(v2_router)
