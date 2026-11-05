"""Shared Jinja2 templates and web asset paths (under project `webapp/`)."""
from pathlib import Path

from fastapi.templating import Jinja2Templates

APP_DIR = Path(__file__).resolve().parent
PROJECT_ROOT = APP_DIR.parent
WEB_ROOT = PROJECT_ROOT / "webapp"

templates = Jinja2Templates(directory=str(WEB_ROOT / "templates"))
STATIC_DIR = WEB_ROOT / "static"
