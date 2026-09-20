import axios from 'axios';
import appConfig from '../../app-config.json';

// Get default URL from config file
const DEFAULT_BASE_URL = appConfig.backend.defaultUrl;

class ApiService {
  constructor() {
    this.baseURL = DEFAULT_BASE_URL;
    this.client = axios.create({
      baseURL: this.baseURL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  // Update base URL
  setBaseURL(url) {
    this.baseURL = url;
    this.client = axios.create({
      baseURL: this.baseURL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  // Get current base URL
  getBaseURL() {
    return this.baseURL;
  }

  // Generic request handler
  async request(method, path, data = null) {
    try {
      const config = {
        method,
        url: path,
      };

      if (data) {
        config.data = data;
      }

      const response = await this.client.request(config);
      return {
        success: true,
        data: response.data,
        status: response.status,
      };
    } catch (error) {
      return {
        success: false,
        error: error.message,
        status: error.response?.status || 0,
      };
    }
  }

  // API Endpoints

  // Get root/api info
  async getApiInfo() {
    return this.request('GET', '/api');
  }

  // Health check
  async getHealth() {
    return this.request('GET', '/health');
  }

  // System info
  async getSystemInfo() {
    return this.request('GET', '/info');
  }

  // Test endpoint with message parameter
  async testMessage(message, format = null) {
    const path = format ? `/test/${message}?format=${format}` : `/test/${message}`;
    return this.request('GET', path);
  }

  // Echo endpoint (POST)
  async echo(message, data = null) {
    return this.request('POST', '/echo', {
      message,
      data,
    });
  }
}

// Create singleton instance
const apiService = new ApiService();

export default apiService;

