# FastAPI Webapp

A modern FastAPI web application with server-rendered Jinja2 pages, static assets and a JSON API.

## Features

- 🚀 FastAPI backend with async support
- 🎨 Modern, responsive UI with custom CSS
- 📱 Mobile-friendly design
- 🔄 Real-time dashboard with live updates
- 📊 System monitoring and analytics
- 🎯 Template-based architecture for easy page additions

## Project Structure

```
/
├── main.py                 # FastAPI application entry point
├── requirements.txt        # Python dependencies
├── README.md               # This file
├── templates/              # Jinja2 HTML templates
│   ├── base.html           # Base template with navigation
│   ├── index.html          # Home page
│   ├── about.html          # About page
│   ├── features.html       # Features page
│   ├── dashboard.html      # Dashboard page
│   └──/
├── static/                 # Static assets
│   ├── css/
│   │   ├── style.css       # Main stylesheet
│   │   └──/
│   ├── js/
│   │   ├── main.js         # JavaScript functionality
│   │   └──/
│   └──/
└──/
```

## Installation

1. **Create a virtual environment:**
```bash
cd fastapi-webapp-poc
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. **Install dependencies:**
```bash
pip install -r requirements.txt
```

## Running the Application

### Development Mode

Run with auto-reload enabled:

```bash
python main.py
```

Or using uvicorn directly:

```bash
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

### Production Mode

```bash
uvicorn main:app --host 0.0.0.0 --port 8000 --workers 4
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

2. **Add a route** in `main.py`:
   ```python
   @app.get("/yourpage", response_class=HTMLResponse)
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

3. **Update navigation** in `templates/base.html` (if needed):
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

Sample project, free to use as a starting point.

