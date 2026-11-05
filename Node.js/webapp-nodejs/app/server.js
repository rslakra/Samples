const express = require('express');
const pino = require('pino');

const PORT = parseInt(process.env.PORT, 10) || 3000;
const HOST = process.env.HOST || '0.0.0.0';

const logger = pino({
    level: process.env.LOG_LEVEL || 'info',
    // pino 9 ships an ISO-8601 timestamp helper, no hand-rolled string needed.
    timestamp: pino.stdTimeFunctions.isoTime
});

const app = express();

// Request logging, so every call shows up in the structured log stream.
app.use((req, res, next) => {
    res.on('finish', () => {
        logger.info({
            method: req.method,
            url: req.originalUrl,
            status: res.statusCode
        }, 'request handled');
    });
    next();
});

// Root route, the container port now actually serves something.
app.get('/', (req, res) => {
    res.json({
        app: 'webapp-nodejs',
        version: require('../package.json').version,
        message: 'hello elastic world'
    });
});

// Liveness/readiness probe target, used by the Justfile, Compose and Kubernetes.
app.get('/health', (req, res) => {
    res.json({
        status: 'UP',
        uptime: process.uptime(),
        time: new Date().toISOString()
    });
});

const server = app.listen(PORT, HOST, () => {
    logger.info(`app listening on http://${HOST}:${PORT}`);
});

// Containers get SIGTERM on stop, so shut down cleanly instead of being killed.
const shutdown = (signal) => {
    logger.info(`${signal} received, shutting down`);
    server.close(() => {
        logger.info('server closed');
        process.exit(0);
    });
};

['SIGTERM', 'SIGINT'].forEach((signal) => process.on(signal, () => shutdown(signal)));
