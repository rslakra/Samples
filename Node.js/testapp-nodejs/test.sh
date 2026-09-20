#!/bin/bash
# Simple test script for Node.js Test App endpoints

PORT=${PORT:-4000}
BASE_URL="http://localhost:${PORT}"

echo "=========================================="
echo "Testing Node.js Test App Endpoints"
echo "=========================================="
echo ""

# Color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Test function
test_endpoint() {
  local method=$1
  local endpoint=$2
  local data=$3
  local description=$4
  
  echo -e "${YELLOW}Testing:${NC} ${description}"
  echo "  ${method} ${BASE_URL}${endpoint}"
  
  if [ "$method" = "GET" ]; then
    response=$(curl -s -w "\n%{http_code}" "${BASE_URL}${endpoint}")
  else
    response=$(curl -s -w "\n%{http_code}" -X "${method}" "${BASE_URL}${endpoint}" \
      -H "Content-Type: application/json" \
      -d "${data}")
  fi
  
  # Extract status code (last line) and body (everything else)
  status_code=$(echo "$response" | tail -n 1)
  body=$(echo "$response" | head -n -1)
  
  if [ "$status_code" = "200" ]; then
    echo -e "  ${GREEN}✓ Success${NC} (${status_code})"
    echo "$body" | jq '.' 2>/dev/null || echo "$body"
  else
    echo -e "  ${RED}✗ Failed${NC} (${status_code})"
    echo "$body"
  fi
  echo ""
}

# Check if server is running
echo "Checking if server is running..."
if ! curl -s "${BASE_URL}/health" > /dev/null 2>&1; then
  echo -e "${RED}Error: Server is not running at ${BASE_URL}${NC}"
  echo "Please start the server with: npm start"
  exit 1
fi
echo -e "${GREEN}✓ Server is running${NC}"
echo ""

# Run tests
test_endpoint "GET" "/" "" "Root endpoint - Application info"
test_endpoint "GET" "/health" "" "Health check endpoint"
test_endpoint "GET" "/info" "" "System information endpoint"
test_endpoint "POST" "/echo" '{"message":"Hello World","data":{"test":true,"number":42}}' "Echo endpoint with JSON data"
test_endpoint "GET" "/test/awesome" "" "Test endpoint with path parameter"
test_endpoint "GET" "/test/hello?format=simple" "" "Test endpoint with query parameter"
test_endpoint "GET" "/nonexistent" "" "404 - Non-existent endpoint"

echo "=========================================="
echo -e "${GREEN}Testing complete!${NC}"
echo "=========================================="

