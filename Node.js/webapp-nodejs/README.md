# webapp-nodejs

A minimal containerized Node.js web app, used as a sample for structured logging
(`pino` JSON output on stdout) and for Docker / Docker Compose / Kubernetes packaging.

## Endpoints

| Method | Path      | Description                               |
|--------|-----------|-------------------------------------------|
| GET    | `/`       | App name, version and a greeting payload  |
| GET    | `/health` | Liveness/readiness probe (`{"status":"UP"}`) |

Every request is logged as a single JSON line with an ISO-8601 `time` field, so the
output can be shipped straight to Elasticsearch/Filebeat.

## Stack

| Dependency | Version   |
|------------|-----------|
| Node.js    | `>= 18` (image: `node:20-alpine`) |
| express    | `^4.21.2` |
| pino       | `^9.6.0`  |

## Configuration

| Variable    | Default   | Description               |
|-------------|-----------|---------------------------|
| `PORT`      | `3000`    | HTTP listen port          |
| `HOST`      | `0.0.0.0` | HTTP bind address         |
| `LOG_LEVEL` | `info`    | pino log level            |

## Run locally

```shell
npm install
npm start
curl http://localhost:3000/health
```

## Docker Commands

```shell
# list all recipes
just

# standalone container
just build-container
just run-container
just curl-container
just log-container
just shell-container
just stop-container

# docker compose
just start-app
just start-bg-app
just log-app
just stop-app
just clean-app

# kubernetes
just k8s-deploy
just k8s-logs
just k8s-delete
```

> `deployment.yaml` uses the locally built `webapp-nodejs:latest` image with
> `imagePullPolicy: IfNotPresent`. Push the image to a registry and switch the policy to
> `Always` (adding `imagePullSecrets` if the registry is private) for a remote cluster.

# Author
- Rohtash Lakra
