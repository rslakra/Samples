"""HTML page routes."""
from fastapi import APIRouter, Request
from fastapi.responses import HTMLResponse

from app.templating import templates

router = APIRouter(tags=["pages"])


@router.get("/", response_class=HTMLResponse)
async def index(request: Request):
    """Render the home page"""
    return templates.TemplateResponse(
        "index.html",
        {
            "request": request,
            "title": "Home",
            "page": "home",
        },
    )


@router.get("/dashboard", response_class=HTMLResponse)
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
                "memory_usage": 54,
            },
        },
    )


@router.get("/features", response_class=HTMLResponse)
async def features(request: Request):
    """Render the features page"""
    return templates.TemplateResponse(
        "features.html",
        {
            "request": request,
            "title": "Features",
            "page": "features",
        },
    )


@router.get("/about", response_class=HTMLResponse)
async def about(request: Request):
    """Render the about page"""
    return templates.TemplateResponse(
        "about.html",
        {
            "request": request,
            "title": "About",
            "page": "about",
        },
    )
