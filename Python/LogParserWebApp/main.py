#
# Author: Rohtash Lakra
# Reference:
#
from fastapi import FastAPI

from api import api_router

# Initialize FastAPI Application
app = FastAPI(
    root_path="/",
    redirect_slashes=False,
    docs_url="/docs",
)


@app.get("/")
def root():
    return {"Hello": "World"}


# Include API Routers
app.include_router(api_router)
