const express = require('express');
const app = express();
const path = require('path');
const { exec } = require('child_process');

// Middleware
app.use(express.json());

// Configuration
const PORT = process.env.PORT || 4000;
const HOST = process.env.HOST || '0.0.0.0';

// Utility function to get system info
function getSystemInfo() {
    return {
        platform: process.platform,
        nodeVersion: process.version,
        architecture: process.arch,
        memory: {
            total: `${(process.memoryUsage().heapTotal / 1024 / 1024).toFixed(2)} MB`,
            used: `${(process.memoryUsage().heapUsed / 1024 / 1024).toFixed(2)} MB`
        },
        uptime: `${process.uptime().toFixed(2)} seconds`,
        pid: process.pid,
        environment: process.env.NODE_ENV || 'development'
    };
}

// Routes

// 1. Health check endpoint
app.get('/health', (req, res) => {
    res.json({
        status: 'healthy',
        timestamp: new Date().toISOString(),
        service: 'nodejs-testapp',
        version: '1.0.0'
    });
});

// 2. System info endpoint
app.get('/info', (req, res) => {
    const systemInfo = getSystemInfo();
    res.json({
        service: 'VDI Apps Container - Node.js Test App',
        version: '1.0.0',
        timestamp: new Date().toISOString(),
        system: systemInfo,
        endpoints: {
            health: '/health',
            info: '/info',
            echo: '/echo (POST)',
            test: '/test/:message (GET)'
        }
    });
});

// 3. Echo endpoint (POST)
app.post('/echo', (req, res) => {
    const { message, data } = req.body;

    res.json({
        echo: {
            message: message || 'No message provided',
            data: data || null
        },
        timestamp: new Date().toISOString(),
        receivedAt: new Date().toLocaleString()
    });
});

// 4. Test endpoint with path parameter
app.get('/test/:message', (req, res) => {
    const { message } = req.params;
    const { format } = req.query;

    const response = {
        message: message,
        uppercased: message.toUpperCase(),
        lowercased: message.toLowerCase(),
        length: message.length,
        reversed: message.split('').reverse().join(''),
        timestamp: new Date().toISOString()
    };

    if (format === 'simple') {
        res.send(`Test message: ${message}`);
    } else {
        res.json(response);
    }
});

// API Root endpoint
app.get('/api', (req, res) => {
    res.json({
        name: 'VDI Apps Container - Node.js Test App',
        version: '1.0.0',
        description: 'Simple test application with basic endpoints',
        endpoints: [
            {
                path: '/health',
                method: 'GET',
                description: 'Health check endpoint'
            },
            {
                path: '/info',
                method: 'GET',
                description: 'Get system and application information'
            },
            {
                path: '/echo',
                method: 'POST',
                description: 'Echo back JSON data',
                example: { message: 'Hello', data: { key: 'value' } }
            },
            {
                path: '/test/:message',
                method: 'GET',
                description: 'Test endpoint with message parameter',
                example: '/test/hello?format=json'
            }
        ],
        status: 'running',
        timestamp: new Date().toISOString()
    });
});

// Serve static files from public directory (after API routes)
app.use(express.static(path.join(__dirname, 'public')));

// 404 handler (only for non-static routes)
app.use((req, res) => {
    // Only return JSON 404 for API routes
    if (req.path.startsWith('/api') || req.path.startsWith('/health') ||
        req.path.startsWith('/info') || req.path.startsWith('/echo') ||
        req.path.startsWith('/test')) {
        res.status(404).json({
            error: 'Not Found',
            message: `Route ${req.method} ${req.path} not found`,
            availableEndpoints: ['/api', '/health', '/info', '/echo', '/test/:message']
        });
    } else {
        res.status(404).send('404 - Page Not Found');
    }
});

// Error handler
app.use((err, req, res, next) => {
    console.error('Error:', err.message);
    res.status(500).json({
        error: 'Internal Server Error',
        message: err.message
    });
});

// Function to open browser
function openBrowser(url) {
    const platform = process.platform;
    let command;

    if (platform === 'darwin') {
        command = `open ${url}`;
    } else if (platform === 'win32') {
        command = `start ${url}`;
    } else {
        command = `xdg-open ${url}`;
    }

    exec(command, (error) => {
        if (error) {
            console.log(`⚠️  Could not auto-open browser: ${error.message}`);
            console.log(`   Please manually open: ${url}`);
        } else {
            console.log(`🌐 Browser opened automatically: ${url}`);
        }
    });
}

// Start server
app.listen(PORT, HOST, () => {
    console.log('='.repeat(50));
    console.log('🚀 VDI Apps Container - Node.js Test App');
    console.log('='.repeat(50));
    console.log(`📡 Server running on http://${HOST}:${PORT}`);
    console.log(`📊 Environment: ${process.env.NODE_ENV || 'development'}`);
    console.log(`🔧 Node.js version: ${process.version}`);
    console.log('');
    console.log('Available endpoints:');
    console.log(`  GET  http://localhost:${PORT}/api`);
    console.log(`  GET  http://localhost:${PORT}/health`);
    console.log(`  GET  http://localhost:${PORT}/info`);
    console.log(`  POST http://localhost:${PORT}/echo`);
    console.log(`  GET  http://localhost:${PORT}/test/:message`);
    console.log('');
    console.log('🎨 Dashboard:');
    console.log(`  http://localhost:${PORT}/`);
    console.log(`  http://localhost:${PORT}/index.html`);
    console.log('='.repeat(50));

    // Auto-open browser after a short delay
    // setTimeout(() => {
    //     openBrowser(`http://localhost:${PORT}/`);
    // }, 1000);
});

// Graceful shutdown
process.on('SIGTERM', () => {
    console.log('SIGTERM signal received: closing HTTP server');
    process.exit(0);
});

process.on('SIGINT', () => {
    console.log('SIGINT signal received: closing HTTP server');
    process.exit(0);
});

