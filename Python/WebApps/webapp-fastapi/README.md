# VDI POC Web Application

A modern FastAPI web application for the Virtual Desktop Infrastructure Proof of Concept project.

## Features

- 🚀 FastAPI backend with async support
- 🎨 Modern, responsive UI with custom CSS
- 📱 Mobile-friendly design
- 🔄 Real-time dashboard with live updates
- 📊 System monitoring and analytics
- 🎯 Template-based architecture for easy page additions

## Project Structure

```
webapp-fastapi/
├── app/
│   ├── main.py              # FastAPI app factory & API routes (e.g. /api/health)
│   ├── templating.py        # Jinja2 + paths to webapp/templates
│   └── routers/
│       └── index.py         # HTML page routes (/, /about, /features, /dashboard)
├── webapp/
│   ├── templates/           # Jinja2 HTML templates
│   │   ├── base.html
│   │   ├── index.html
│   │   ├── about.html
│   │   ├── features.html
│   │   └── dashboard.html
│   └── static/              # CSS, JS (served at /static)
├── requirements.txt
├── start.sh
└── README.md
```

## Installation

1. **Create a virtual environment:**
```bash
cd webapp-fastapi
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
pip install --upgrade pip
```

2. **Install dependencies:**
```bash
pip install -r requirements.txt
```

## Running the Application

Run from the **webapp-fastapi** directory (the parent of `app/`) so imports resolve.

### Development Mode

```bash
./start.sh
```

Or:

```bash
python -m uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

### Production Mode

```bash
uvicorn app.main:app --host 0.0.0.0 --port 8000 --workers 4
```

## Accessing the Application

Once started, the application will be available at:
- **Local:** http://localhost:8000
- **Network:** http://0.0.0.0:8000

### Available Routes

- `/` - Home page
- `/about` - About page
- `/features` - Features overview
- `/dashboard` - Real-time monitoring dashboard
- `/api/health` - Health check API endpoint

## Adding New Pages

To add a new page to the application:

1. **Create a new template** in `templates/` directory:
   ```html
   {% extends "base.html" %}
   
   {% block title %}Your Page Title{% endblock %}
   
   {% block content %}
   <div class="page-header">
       <div class="container">
           <h1>Your Page Title</h1>
       </div>
   </div>
   
   <section class="section">
       <div class="container">
           <!-- Your content here -->
       </div>
   </section>
   {% endblock %}
   ```

2. **Add a route** in `app/routers/index.py` (or a new router included from `app/main.py`):
   ```python
   @router.get("/yourpage", response_class=HTMLResponse)
   async def your_page(request: Request):
       return templates.TemplateResponse(
           "yourpage.html",
           {
               "request": request,
               "title": "Your Page",
               "page": "yourpage"
           }
       )
   ```

3. **Update navigation** in `webapp/templates/base.html` (if needed):
   ```html
   <li><a href="/yourpage" class="{% if page == 'yourpage' %}active{% endif %}">Your Page</a></li>
   ```

## API Documentation

When running, visit http://localhost:8000/docs for interactive API documentation (Swagger UI).

## Technologies Used

- **FastAPI** - Modern, fast web framework
- **Uvicorn** - ASGI server
- **Jinja2** - Template engine
- **CSS3** - Modern styling
- **Vanilla JavaScript** - No framework dependencies

## Development Tips

- Templates use Jinja2 syntax for dynamic content
- All templates extend `base.html` for consistent layout
- Static files (CSS/JS) are served from the `/static` directory
- Use the `page` variable in templates to highlight active navigation items
- Dashboard updates automatically every 30 seconds

## Performance

- Async/await support for high concurrency
- Static file caching enabled
- Gzip compression supported
- Production-ready with proper error handling

## Browser Support

- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)
- Mobile browsers (iOS Safari, Chrome Mobile)

## License

Part of the VDI POC project.

