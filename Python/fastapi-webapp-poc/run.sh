#!/bin/bash
# Quick start script for the FastAPI Webapp

set -e

echo "🚀 Starting FastAPI Webapp..."
echo ""

# Check if virtual environment exists
if [ ! -d "venv" ]; then
    echo "📦 Creating virtual environment..."
    # python3 -m pip install virtualenv
    python3 -m venv venv
fi

# Activate virtual environment
echo "🔧 Activating virtual environment..."
source venv/bin/activate


# Install dependencies
echo "📥 Installing dependencies..."
pip install -q -r requirements.txt

echo ""
echo "✅ Setup complete!"
echo ""
echo "🌐 Starting web server at http://localhost:8000"
echo "📊 API documentation at http://localhost:8000/docs"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

# Run with auto-reload (Python, templates, static CSS)
python -m uvicorn main:app --host 0.0.0.0 --port 8000 --reload

