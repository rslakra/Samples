# Node 13 is long EOL, pinned to an active LTS line instead.
FROM node:20-alpine

ENV NODE_ENV=production \
    PORT=3000

WORKDIR /usr/app

COPY package*.json ./

# package-lock.json is lockfileVersion 3, so builds are reproducible via `npm ci`.
RUN npm ci --omit=dev && npm cache clean --force

# Copy the app directory itself, `COPY app/* ` used to flatten it into /usr/app.
COPY app/ ./app/

# Run as the unprivileged user that ships with the node image.
USER node

EXPOSE 3000

CMD ["node", "app/server.js"]
