"""
FastAPI Web Application
Main application entry point with routing and template rendering.
"""
from fastapi import FastAPI, Request
from fastapi.responses import HTMLResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from pathlib import Path

# Initialize FastAPI app
app = FastAPI(
    title="FastAPI Webapp",
    description="A modern FastAPI web application with server-rendered pages",
    version="1.0.0"
)

# Setup static files and templates
BASE_DIR = Path(__file__).resolve().parent
app.mount("/static", StaticFiles(directory=str(BASE_DIR / "static")), name="static")
templates = Jinja2Templates(directory=str(BASE_DIR / "templates"))


@app.get("/", response_class=HTMLResponse)
async def index(request: Request):
    """Render the home page"""
    return templates.TemplateResponse(
        "index.html",
        {
            "request": request,
            "title": "Home",
            "page": "home"
        }
    )


@app.get("/about", response_class=HTMLResponse)
async def about(request: Request):
    """Render the about page"""
    return templates.TemplateResponse(
        "about.html",
        {
            "request": request,
            "title": "About",
            "page": "about"
        }
    )


@app.get("/features", response_class=HTMLResponse)
async def features(request: Request):
    """Render the features page"""
    return templates.TemplateResponse(
        "features.html",
        {
            "request": request,
            "title": "Features",
            "page": "features"
        }
    )


@app.get("/dashboard", response_class=HTMLResponse)
async def dashboard(request: Request):
    """Render the dashboard page"""
    return templates.TemplateResponse(
        "dashboard.html",
        {
            "request": request,
            "title": "Dashboard",
            "page": "dashboard",
            "stats": {
                "active_sessions": 42,
                "total_users": 156,
                "cpu_usage": 67,
                "memory_usage": 54
            }
        }
    )


@app.get("/api/health")
async def health_check():
    """API endpoint for health check"""
    return {
        "status": "healthy",
        "version": "1.0.0"
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)

