#!/bin/bash

###############################################################################
# Zip Project Script
# Creates a distributable zip archive of the nodejs-testapp project
#
# Usage:
#   ./zip-project.sh [--include-node-modules]
#
# Options:
#   (default)                - Exclude node_modules folder
#   --include-node-modules   - Include node_modules folder
#
# Note: Always excludes .git, .DS_Store, logs, and other unwanted files
###############################################################################

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Configuration
PROJECT_NAME="nodejs-testapp"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="${PROJECT_NAME}.zip"
OUTPUT_FILE_TIMESTAMPED="${PROJECT_NAME}_${TIMESTAMP}.zip"

# Parse command line arguments
EXCLUDE_NODE_MODULES=true
if [ "$1" == "--include-node-modules" ]; then
    EXCLUDE_NODE_MODULES=false
fi

echo -e "${BLUE}=================================${NC}"
echo -e "${BLUE}   Project Zip Archive Creator${NC}"
echo -e "${BLUE}=================================${NC}"
echo ""

# Get the directory where the script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$SCRIPT_DIR"
PARENT_DIR="$(dirname "$PROJECT_DIR")"
PROJECT_BASENAME="$(basename "$PROJECT_DIR")"

echo -e "${YELLOW}Project Directory:${NC} $PROJECT_DIR"
echo -e "${YELLOW}Output Directory:${NC} $PARENT_DIR"
echo ""

# Check if we're in the right directory
if [ ! -f "$PROJECT_DIR/package.json" ]; then
    echo -e "${RED}Error: package.json not found. Are you in the correct directory?${NC}"
    exit 1
fi

# Change to parent directory for zipping
cd "$PARENT_DIR" || exit 1

# Define common exclusion patterns (always excluded)
COMMON_EXCLUDES=(
    "*/.DS_Store"
    "*/.git/*"
    "*/npm-debug.log*"
    "*/.npm"
    "*/.eslintcache"
    "*/coverage/*"
    "*/.nyc_output/*"
    "*/.vscode/*"
    "*/.idea/*"
    "*.log"
    "*.swp"
    "*.swo"
    "*~"
)

# Build exclusion arguments
EXCLUDE_ARGS=()
for pattern in "${COMMON_EXCLUDES[@]}"; do
    EXCLUDE_ARGS+=("-x" "$pattern")
done

# Add node_modules exclusion if needed
if [ "$EXCLUDE_NODE_MODULES" = true ]; then
    echo -e "${BLUE}Creating zip archive (excluding node_modules)...${NC}"
    echo -e "${YELLOW}Excluding:${NC} node_modules, .git, .DS_Store, logs, and dev files"
    EXCLUDE_ARGS+=("-x" "*/node_modules/*")
else
    echo -e "${BLUE}Creating zip archive (including node_modules)...${NC}"
    echo -e "${YELLOW}Including:${NC} node_modules folder (may be large)"
    echo -e "${YELLOW}Excluding:${NC} .git, .DS_Store, logs, and dev files"
fi

echo ""

# Create zip with appropriate exclusions
zip -r "$OUTPUT_FILE" "$PROJECT_BASENAME" "${EXCLUDE_ARGS[@]}" > /dev/null 2>&1

# Check if zip was successful
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Archive created successfully!${NC}"
    echo ""
    
    # Get file size
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        FILE_SIZE=$(ls -lh "$OUTPUT_FILE" | awk '{print $5}')
    else
        # Linux
        FILE_SIZE=$(ls -lh "$OUTPUT_FILE" | awk '{print $5}')
    fi
    
    echo -e "${GREEN}Archive Details:${NC}"
    echo -e "  File: ${YELLOW}$OUTPUT_FILE${NC}"
    echo -e "  Size: ${YELLOW}$FILE_SIZE${NC}"
    echo -e "  Location: ${YELLOW}$PARENT_DIR/$OUTPUT_FILE${NC}"
    echo ""
    
    # List contents
    echo -e "${BLUE}Archive Contents:${NC}"
    unzip -l "$OUTPUT_FILE" | head -20
    
    # Count total files
    TOTAL_FILES=$(unzip -l "$OUTPUT_FILE" | tail -1 | awk '{print $2}')
    echo ""
    echo -e "${GREEN}Total files in archive: $TOTAL_FILES${NC}"
    
    # Ask if user wants a timestamped version
    echo ""
    read -p "Create timestamped version? (y/n) " -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        cp "$OUTPUT_FILE" "$OUTPUT_FILE_TIMESTAMPED"
        echo -e "${GREEN}✓ Timestamped version created: $OUTPUT_FILE_TIMESTAMPED${NC}"
    fi
    
    echo ""
    echo -e "${GREEN}=================================${NC}"
    echo -e "${GREEN}   ✓ Zip Archive Ready!${NC}"
    echo -e "${GREEN}=================================${NC}"
    
else
    echo -e "${RED}✗ Error creating archive${NC}"
    exit 1
fi

# Return to original directory
cd "$PROJECT_DIR" || exit 1
