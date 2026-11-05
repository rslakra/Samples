PYTHON ?= python3
ROOT := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
VENV := $(ROOT)venv
PIP := $(VENV)/bin/pip
UVICORN := $(VENV)/bin/uvicorn
HOST ?= 0.0.0.0
PORT ?= 8000
WORKERS ?= 4

.DEFAULT_GOAL := help

.PHONY: help setup install start stop restart prod health clean

help: ## Show available commands
	@echo "FastAPI Webapp — FastAPI + Jinja2"
	@echo ""
	@echo "Usage: make [target]"
	@echo ""
	@grep -E '^[a-zA-Z0-9_-]+:.*##' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*## "}; {printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2}'
	@echo ""
	@echo "First-time workflow:"
	@echo "  make setup"
	@echo "  make start"
	@echo ""
	@echo "App:  http://localhost:$(PORT)"
	@echo "Docs: http://localhost:$(PORT)/docs"

setup: ## Create the virtual environment and install dependencies
	@test -d $(VENV) || $(PYTHON) -m venv $(VENV)
	@$(MAKE) install

install: ## Install or upgrade packages from requirements.txt
	@test -d $(VENV) || (echo "Run 'make setup' first to create the virtual environment." && exit 1)
	$(PIP) install --upgrade pip
	$(PIP) install -r $(ROOT)requirements.txt

start: install ## Run the app with uvicorn and auto-reload
	@$(MAKE) stop
	@echo "FastAPI Webapp: http://localhost:$(PORT)  (docs at /docs)"
	cd $(ROOT) && $(UVICORN) app.main:app --host $(HOST) --port $(PORT) --reload

prod: install ## Run the app without reload using multiple workers
	@$(MAKE) stop
	cd $(ROOT) && $(UVICORN) app.main:app --host $(HOST) --port $(PORT) --workers $(WORKERS)

stop: ## Stop any process listening on the app port
	@pids=$$(lsof -tiTCP:$(PORT) -sTCP:LISTEN 2>/dev/null); \
	if [ -n "$$pids" ]; then \
		echo "Stopping process on port $(PORT) ($$pids)..."; \
		kill $$pids 2>/dev/null || true; \
		sleep 1; \
		pids=$$(lsof -tiTCP:$(PORT) -sTCP:LISTEN 2>/dev/null); \
		if [ -n "$$pids" ]; then \
			kill -9 $$pids 2>/dev/null || true; \
			sleep 1; \
		fi; \
	else \
		echo "Nothing listening on port $(PORT)."; \
	fi

restart: stop start ## Stop then start the app

health: ## Call the /api/health endpoint of the running app
	@curl -fsS http://localhost:$(PORT)/api/health || (echo "App is not responding on port $(PORT). Run 'make start'." && exit 1)
	@echo ""

clean: ## Remove venv and Python cache files
	rm -rf $(VENV)
	find $(ROOT) -type d -name __pycache__ -exec rm -rf {} + 2>/dev/null || true
	find $(ROOT) -type f -name '*.py[co]' -delete 2>/dev/null || true
