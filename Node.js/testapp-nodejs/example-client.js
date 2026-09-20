#!/usr/bin/env node

/**
 * Example client demonstrating how to interact with the Node.js Test App
 * Run with: node example-client.js
 */

const http = require('http');

const BASE_URL = 'http://localhost:4000';

// Utility function to make HTTP requests
function makeRequest(method, path, data = null) {
    return new Promise((resolve, reject) => {
        const url = new URL(path, BASE_URL);
        const options = {
            hostname: url.hostname,
            port: url.port,
            path: url.pathname + url.search,
            method: method,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        const req = http.request(options, (res) => {
            let body = '';

            res.on('data', (chunk) => {
                body += chunk;
            });

            res.on('end', () => {
                try {
                    const response = {
                        statusCode: res.statusCode,
                        headers: res.headers,
                        body: JSON.parse(body)
                    };
                    resolve(response);
                } catch (error) {
                    resolve({
                        statusCode: res.statusCode,
                        headers: res.headers,
                        body: body
                    });
                }
            });
        });

        req.on('error', (error) => {
            reject(error);
        });

        if (data) {
            req.write(JSON.stringify(data));
        }

        req.end();
    });
}

// Example functions for each endpoint

async function testHealth() {
    console.log('\n📊 Testing /health endpoint...');
    try {
        const response = await makeRequest('GET', '/health');
        console.log('✅ Status:', response.statusCode);
        console.log('   Response:', JSON.stringify(response.body, null, 2));
    } catch (error) {
        console.error('❌ Error:', error.message);
    }
}

async function testInfo() {
    console.log('\n📋 Testing /info endpoint...');
    try {
        const response = await makeRequest('GET', '/info');
        console.log('✅ Status:', response.statusCode);
        console.log('   Service:', response.body.service);
        console.log('   System:', JSON.stringify(response.body.system, null, 2));
    } catch (error) {
        console.error('❌ Error:', error.message);
    }
}

async function testEcho() {
    console.log('\n🔄 Testing /echo endpoint...');
    const testData = {
        message: 'Hello from example client!',
        data: {
            timestamp: new Date().toISOString(),
            random: Math.floor(Math.random() * 1000),
            nested: {
                key: 'value',
                array: [1, 2, 3]
            }
        }
    };

    try {
        const response = await makeRequest('POST', '/echo', testData);
        console.log('✅ Status:', response.statusCode);
        console.log('   Echo:', JSON.stringify(response.body, null, 2));
    } catch (error) {
        console.error('❌ Error:', error.message);
    }
}

async function testParameter() {
    console.log('\n🧪 Testing /test/:message endpoint...');
    const messages = ['hello', 'world', 'nodejs'];

    for (const message of messages) {
        try {
            const response = await makeRequest('GET', `/test/${message}`);
            console.log(`✅ Testing with "${message}":`, response.body.uppercased);
        } catch (error) {
            console.error('❌ Error:', error.message);
        }
    }
}

async function testRoot() {
    console.log('\n🏠 Testing / (root) endpoint...');
    try {
        const response = await makeRequest('GET', '/');
        console.log('✅ Status:', response.statusCode);
        console.log('   Name:', response.body.name);
        console.log('   Version:', response.body.version);
        console.log('   Endpoints:', response.body.endpoints.length);
    } catch (error) {
        console.error('❌ Error:', error.message);
    }
}

async function testSequential() {
    console.log('\n⏱️  Testing sequential requests...');
    const start = Date.now();

    for (let i = 1; i <= 5; i++) {
        try {
            await makeRequest('GET', '/health');
            console.log(`  Request ${i}: ✓`);
        } catch (error) {
            console.log(`  Request ${i}: ✗`);
        }
    }

    const duration = Date.now() - start;
    console.log(`✅ Completed 5 requests in ${duration}ms (avg: ${(duration / 5).toFixed(2)}ms)`);
}

async function testConcurrent() {
    console.log('\n🚀 Testing concurrent requests...');
    const start = Date.now();

    const promises = [];
    for (let i = 1; i <= 10; i++) {
        promises.push(makeRequest('GET', '/health'));
    }

    try {
        await Promise.all(promises);
        const duration = Date.now() - start;
        console.log(`✅ Completed 10 concurrent requests in ${duration}ms (avg: ${(duration / 10).toFixed(2)}ms)`);
    } catch (error) {
        console.error('❌ Error:', error.message);
    }
}

// Main execution
async function main() {
    console.log('='.repeat(60));
    console.log('🧪 Node.js Test App - Example Client');
    console.log('='.repeat(60));
    console.log('Testing server at:', BASE_URL);

    // Check if server is running
    try {
        await makeRequest('GET', '/health');
        console.log('✅ Server is reachable');
    } catch (error) {
        console.error('❌ Cannot connect to server:', error.message);
        console.error('   Make sure the server is running with: npm start');
        process.exit(1);
    }

    // Run all tests
    await testRoot();
    await testHealth();
    await testInfo();
    await testEcho();
    await testParameter();
    await testSequential();
    await testConcurrent();

    console.log('\n' + '='.repeat(60));
    console.log('✅ All tests completed!');
    console.log('='.repeat(60));
}

// Run the main function
if (require.main === module) {
    main().catch((error) => {
        console.error('Fatal error:', error);
        process.exit(1);
    });
}

module.exports = { makeRequest };

