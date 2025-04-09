# Specifies the Python base image.
#FROM public.ecr.aws/docker/library/python:3.13.1-slim-bullseye
FROM python:3.12-slim

# Arguments
ARG APP_ENV=develop

# Define Env Variables
ENV APP_ENV=$APP_ENV
ENV APP_NAME=fastapi-webapp
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

LABEL maintainer="Rohtash Lakra <work.lakra@gmail.com>"

# Sets the working directory inside the Docker container at `/app`
WORKDIR /app

# Copies the current directory (.) from the host machine to the `/app` directory in the Docker container.
COPY . /app

# moved these configs to ENV variables and removed testing file
#COPY default.env ./.env

# Installs the Python dependencies specified in the `requirements.txt` file.
#RUN pip install --upgrade pip
#RUN pip install -r requirements.txt
#RUN pip install --no-cache-dir -r requirements.txt
#RUN pip install -r requirements.txt --no-cache-dir
RUN pip install --upgrade pip && \
    pip install -r requirements.txt --no-cache-dir
#RUN echo ls -la
#RUN ls -la
# Copy the rest of your application code into the container at /app
#COPY . .

# Set logging directory for gunicorn
#ENV ACCESS_LOG="/var/log/gunicorn/access.log"
#ENV ERROR_LOG="/var/log/gunicorn/error.log"

# EXPOSE is for documentation only - does not do anything
# 8080 is the default port the server is listening on
EXPOSE 8080

RUN mkdir /var/log/gunicorn

#HEALTHCHECK --interval=15s --timeout=5s --start-period=5s --retries=3 CMD  curl --silent --fail 0.0.0.0:8082/health_check || exit 1

# Run Flask app
# python -m flask --app wsgi run --port 8080 --debug
# If needs override answers Yes, use CMD else ENTRYPOINT
#CMD ["python", "-m", "flask", "--app", "wsgi", "run", "--port=8080", "--host=0.0.0.0"]
#ENTRYPOINT ["python", "-m", "flask", "--app", "wsgi", "run", "--port=8080", "--host=0.0.0.0"]

# Run Gunicorn app
# gunicorn -c gunicorn.conf.py wsgi:app
# gunicorn --name "gunicorn" -c gunicorn.conf.py wsgi:app
# gunicorn --bind "0.0.0.0:8080" -c gunicorn.conf.py wsgi:app
# If needs override answers Yes, use CMD else ENTRYPOINT
CMD ["gunicorn", "-c", "gunicorn.conf.py", "wsgi:app"]
#ENTRYPOINT ["gunicorn", "-c", "gunicorn.conf.py", "wsgi:app"]
#CMD ["gunicorn", "--bind", "0.0.0.0:8080", "-c", "gunicorn.conf.py", "wsgi:app"]
#ENTRYPOINT ["gunicorn", "--bind", "0.0.0.0:8080", "-c", "gunicorn.conf.py", "wsgi:app"]
#ENTRYPOINT ["/bin/sh", "-c", "./runGunicorn.sh"]
