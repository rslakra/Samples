# Node.js Test App

A simple Express.js test application for the VDI Apps Container with basic endpoints for testing and demonstration purposes.

## Features

- 🏥 Health check endpoint
- 📊 System information endpoint
- 🔄 Echo endpoint for testing POST requests
- 🧪 Test endpoint with path parameters and query strings
- 📝 JSON responses
- ⚡ Lightweight and fast
- 🎨 **Web Dashboard** - Beautiful UI that auto-launches on server start

## Installation

```bash
cd nodejs-testapp
npm install
```

## Running the Application

### Production Mode
```bash
npm start
```

The server will:
1. Start on `http://0.0.0.0:4000`
2. **Automatically open your browser** with the dashboard
3. Display the interactive web dashboard at `http://localhost:4000/index.html`

### Development Mode (with auto-reload)
```bash
npm run dev
```

By default, the server runs on `http://localhost:4000`.

## Web Dashboard

When you run `npm start`, a beautiful web dashboard automatically opens in your browser. The dashboard provides:

### Features
- 📊 **Real-time API monitoring** for all endpoints
- 🔄 **Auto-refresh capability** (optional 5-second intervals)
- 🧪 **Interactive endpoint testing** - Click to test any endpoint
- 🎨 **Modern UI** with gradient design and smooth animations
- 📱 **Responsive layout** that works on all screen sizes
- 📈 **Live system metrics** (memory, uptime, Node.js version)
- 🟢 **Visual health indicator** shows server status in real-time

### Dashboard Sections
1. **Root Info** - Application details and available endpoints
2. **Health Check** - Server health status
3. **System Info** - Node.js version, memory usage, platform info
4. **Test Endpoint** - Interactive testing with path parameters
5. **Available Endpoints** - Quick access list with method indicators
6. **Echo Test** - POST endpoint testing with JSON data

### Usage
- Click any **Refresh** button to update specific endpoint data
- Click on **endpoint items** in the list to test them interactively
- Enable **Auto-refresh** to update all data every 5 seconds
- View **formatted JSON responses** in easy-to-read containers

### Manual Access
If the browser doesn't auto-open, navigate to:
```
http://localhost:4000/index.html
```

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `4000` | Server port |
| `HOST` | `0.0.0.0` | Server host |
| `NODE_ENV` | `development` | Environment |

## API Endpoints

### 1. Root - Application Info
```bash
GET http://localhost:4000/api
```

**Response:**
```json
{
  "name": "VDI Apps Container - Node.js Test App",
  "version": "1.0.0",
  "description": "Simple test application with basic endpoints",
  "endpoints": [...],
  "status": "running",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### 2. Health Check
```bash
GET http://localhost:4000/health
```

**Response:**
```json
{
  "status": "healthy",
  "timestamp": "2024-01-15T10:30:00.000Z",
  "service": "nodejs-testapp",
  "version": "1.0.0"
}
```

### 3. System Information
```bash
GET http://localhost:4000/info
```

**Response:**
```json
{
  "service": "VDI Apps Container - Node.js Test App",
  "version": "1.0.0",
  "timestamp": "2024-01-15T10:30:00.000Z",
  "system": {
    "platform": "linux",
    "nodeVersion": "v18.17.0",
    "architecture": "x64",
    "memory": {
      "total": "25.50 MB",
      "used": "15.30 MB"
    },
    "uptime": "123.45 seconds",
    "pid": 12345,
    "environment": "development"
  },
  "endpoints": {...}
}
```

### 4. Echo Endpoint (POST)
```bash
POST http://localhost:4000/echo
Content-Type: application/json

{
  "message": "Hello World",
  "data": {
    "key": "value",
    "number": 42
  }
}
```

**Response:**
```json
{
  "echo": {
    "message": "Hello World",
    "data": {
      "key": "value",
      "number": 42
    }
  },
  "timestamp": "2024-01-15T10:30:00.000Z",
  "receivedAt": "1/15/2024, 10:30:00 AM"
}
```

### 5. Test Endpoint (GET with parameters)
```bash
GET http://localhost:4000/test/hello
```

**Response:**
```json
{
  "message": "hello",
  "uppercased": "HELLO",
  "lowercased": "hello",
  "length": 5,
  "reversed": "olleh",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

**With format query parameter:**
```bash
GET http://localhost:4000/test/hello?format=simple
```

**Response:**
```
Test message: hello
```

## Usage Examples

### Using curl

```bash
# Health check
curl http://localhost:4000/health

# Get system info
curl http://localhost:4000/info

# Echo POST request
curl -X POST http://localhost:4000/echo \
  -H "Content-Type: application/json" \
  -d '{"message":"test","data":{"foo":"bar"}}'

# Test with parameter
curl http://localhost:4000/test/awesome

# Test with query string
curl http://localhost:4000/test/awesome?format=simple
```

### Using httpx (Python)

```python
import httpx

# Health check
response = httpx.get("http://localhost:4000/health")
print(response.json())

# Echo
response = httpx.post(
    "http://localhost:4000/echo",
    json={"message": "Hello from Python", "data": {"test": True}}
)
print(response.json())
```

### Using JavaScript/Fetch

```javascript
// Health check
fetch('http://localhost:4000/health')
  .then(res => res.json())
  .then(data => console.log(data));

// Echo
fetch('http://localhost:4000/echo', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    message: 'Hello from JavaScript',
    data: { test: true }
  })
})
  .then(res => res.json())
  .then(data => console.log(data));
```

## Integration with VDI Apps Container

This test app can be used alongside the FastAPI application in the VDI Apps Container:

- **FastAPI**: Port 8000
- **Test App**: Port 4000
- **Navigation Server**: Port 3001
- **Health Server**: Port 3000

You can access it from Chrome within the VDI session at `http://localhost:4000`.

## Docker Support

To run in Docker:

```bash
# Build
docker build -t nodejs-testapp .

# Run
docker run -p 4000:4000 nodejs-testapp
```

## Testing

```bash
# Quick test all endpoints
curl http://localhost:4000/api
curl http://localhost:4000/health
curl http://localhost:4000/info
curl -X POST http://localhost:4000/echo -H "Content-Type: application/json" -d '{"message":"test"}'
curl http://localhost:4000/test/demo
```

## Package for Distribution

Use the included script to create a zip archive:

```bash
./zip-project.sh
```

This will create `nodejs-testapp.zip` excluding node_modules and other unnecessary files.

## License

MIT
