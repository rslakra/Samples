# Backend Server for React Native Test App

This is the Node.js Express backend that serves the API endpoints for the React Native mobile app.

## Features

- Health check endpoint (`/health`)
- System information endpoint (`/info`)
- Echo endpoint for POST testing (`/echo`)
- Test endpoint with parameters (`/test/:message`)
- Web dashboard at `/index.html`

## Quick Start

```bash
# From the backend folder
cd backend
npm install
npm start
```

Server will run on `http://0.0.0.0:4000`

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `4000` | Server port |
| `HOST` | `0.0.0.0` | Server host |
| `NODE_ENV` | `development` | Environment |

## API Endpoints

- `GET /api` - Application information
- `GET /health` - Health check
- `GET /info` - System information
- `GET /test/:message` - Test with parameter
- `POST /echo` - Echo JSON data

## Web Dashboard

Access the web dashboard at: `http://localhost:4000/index.html`

## Docker Support

```bash
# Build and run with Docker
docker-compose up
```

---

This backend is a standalone copy integrated into the React Native project for convenience.

