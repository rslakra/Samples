"""
FastAPI Web Application
Main application entry point with routing and template rendering.
"""
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles

from app.routers import index as index_pages
from app.templating import STATIC_DIR

# Initialize FastAPI app
app = FastAPI(
    title="VDI POC Web Application",
    description="A modern web application for VDI Proof of Concept",
    version="1.0.0",
)

app.mount("/static", StaticFiles(directory=str(STATIC_DIR)), name="static")
app.include_router(index_pages.router)


@app.get("/api/health")
async def health_check():
    """API endpoint for health check"""
    return {
        "status": "healthy",
        "version": "1.0.0",
    }


if __name__ == "__main__":
    import uvicorn

    uvicorn.run("app.main:app", host="0.0.0.0", port=8000, reload=True)
