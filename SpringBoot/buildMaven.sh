#!/bin/bash
# Author: Rohtash Lakra
# Builds all Spring Boot projects under the SpringBoot directory
clear
VERSION="0.0"

# Build Version Function
function buildVersion() {
  GIT_COMMIT_COUNT=$(git rev-list HEAD --count)
  if [ $? -ne 0 ]; then
    VERSION="${VERSION}.1"
  else
    VERSION="${VERSION}.${GIT_COMMIT_COUNT}"
  fi
  SNAPSHOT="${SNAPSHOT:-$!}"
  if [[ ! -z ${SNAPSHOT} ]]; then
      VERSION="${VERSION}-SNAPSHOT"
  fi

  echo "${VERSION}";
}

echo
echo "=========================================="
echo "Building All Spring Boot Projects"
echo "=========================================="
echo "${JAVA_HOME}"
echo

# Calculate versions
SNAPSHOT_VERSION=$(buildVersion SNAPSHOT)
RELEASE_VERSION=$(buildVersion)

echo "SNAPSHOT Version: ${SNAPSHOT_VERSION}"
echo "RELEASE Version: ${RELEASE_VERSION}"
echo

# Find all directories with pom.xml files (excluding target directories)
PROJECTS=$(find . -maxdepth 2 -name "pom.xml" -type f | grep -v target | sed 's|./||' | sed 's|/pom.xml||' | sort)

if [ -z "$PROJECTS" ]; then
    echo "No Maven projects found!"
    exit 1
fi

echo "Found projects:"
echo "$PROJECTS" | sed 's/^/  - /'
echo

# Build each project
FAILED_PROJECTS=()
SUCCESSFUL_PROJECTS=()

for PROJECT in $PROJECTS; do
    echo "=========================================="
    echo "Building: $PROJECT"
    echo "=========================================="
    cd "$PROJECT" || continue
    
    # Build with SNAPSHOT version
    if mvn clean install -Drevision=$SNAPSHOT_VERSION; then
        # Install with RELEASE version (skip tests)
        if mvn install -Drevision=$RELEASE_VERSION -DskipTests=true; then
            SUCCESSFUL_PROJECTS+=("$PROJECT")
            echo "✓ Successfully built: $PROJECT"
        else
            FAILED_PROJECTS+=("$PROJECT (release install failed)")
            echo "✗ Failed to install release version: $PROJECT"
        fi
    else
        FAILED_PROJECTS+=("$PROJECT (build failed)")
        echo "✗ Failed to build: $PROJECT"
    fi
    
    cd - > /dev/null
    echo
done

# Summary
echo "=========================================="
echo "Build Summary"
echo "=========================================="
echo "Successful: ${#SUCCESSFUL_PROJECTS[@]}"
if [ ${#SUCCESSFUL_PROJECTS[@]} -gt 0 ]; then
    for PROJECT in "${SUCCESSFUL_PROJECTS[@]}"; do
        echo "  ✓ $PROJECT"
    done
fi

echo "Failed: ${#FAILED_PROJECTS[@]}"
if [ ${#FAILED_PROJECTS[@]} -gt 0 ]; then
    for PROJECT in "${FAILED_PROJECTS[@]}"; do
        echo "  ✗ $PROJECT"
    done
    exit 1
fi

echo
echo "All projects built successfully!"
echo
