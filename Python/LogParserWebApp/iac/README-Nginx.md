# Nginx

---

The ```Nginx``` contains the python web server with logging capabilities.


## Project Structure
```
/
├── <modules>                       # The name of the module
├── logger                          # The custom logger
│    ├── crawler                    # crawler
│    ├── indexer                    # indexer
│    ├── searcher                   # searcher
│    ├── v1                         # v1 blueprints/endpoints
│    └── __init__.py                # The package initializer
├── tests                           # The service test-cases
│    ├── data                       # data
│    ├── __init__.py                # The package initializer
│    └── __init__.py                # The package initializer
├── webapp                          # The web server
│    ├── static                     # The static contents like css, js etc.
│    │    ├── css                   # css files
│    │    ├── images                # image files
│    │    ├── js                    # JavaScript files
│    │    └── __init__.py           # The package initializer
│    ├── templates                  # The web templates like fragments, html pages etc.
│    │    ├── fragments             # The HTML views/pages
│    │    └── __init__.py           # The package initializer
│    ├── __init__.py                # The package initializer
│    ├── config.py                  # The webapp's configuration file
│    └── routes.py                  # Routes of the webapp UI
├── .env                            # The .env file
├── .gitignore                      # The .gitignore file
├── default.env                     # The default .env file
├── gunicorn.conf.py                # The gunicorn configurations
├── README.md                       # Instructions and helpful links
├── requirements.txt                # The webapp's dependencies/packages
├── robots.txt                      # tells which URLs the search engine crawlers can access on your site
├── wsgi.py                         # the WSGI app
├── <module>                        # The module service
└── /
```

## Local Development


### Docker Commands

#### Builds the docker container image
```shell
docker build -t fastapi-nginx:latest -f iac/Dockerfile.nginx .
```
OR
```shell
docker build -t fastapi-nginx:latest .
```

#### Runs the docker container as background service
```shell
docker run --name fastapi-nginx -p 8080:8080 -d logparser-webapp:latest
```
OR
```shell
docker run --name logparser-webapp --rm -p 8080:8080 -d logparser-webapp:latest
```

#### Shows the docker container's log
```shell
docker logs -f logparser-webapp
```

#### Executes the 'bash' shell in the container
```shell
docker exec -it logparser-webapp bash
```

#### Stops and Remove the docker container

```shell
docker stop logparser-webapp && docker container rm logparser-webapp
```


## Docker Compose

### Deploy with ```docker compose```
```shell
docker compose up -d
```

### Stop and remove the containers
```shell
docker compose down
```


## Save Requirements (Dependencies)
```shell
pip freeze > requirements.txt
```


## Testing

### Unit Tests

- How to run unit-tests?

```shell
python3 -m unittest
python -m unittest discover -s ./tests -p "test_*.py"
```



# Reference

---

- [Gunicorn](https://flask.palletsprojects.com/en/3.0.x/deploying/gunicorn/)
- [Gunicorn - WSGI server](https://docs.gunicorn.org/en/latest/index.html)
- [wsgiref — WSGI Utilities and Reference Implementation](https://docs.python.org/3/library/wsgiref.html)
- [Python Packaging User Guide](https://packaging.python.org/en/latest/)
- [Flask](https://flask.palletsprojects.com/en/3.0.x/)
- [The best Python HTTP clients](https://www.scrapingbee.com/blog/best-python-http-clients/)
- [Python Paste](https://pythonpaste.readthedocs.io/en/latest/index.html)
- [WSGI Servers](https://www.fullstackpython.com/wsgi-servers.html)
- [WSGI: The Server-Application Interface for Python](https://www.toptal.com/python/pythons-wsgi-server-application-interface)
- [Python FastAPI vs Flask: A Detailed Comparison](https://www.turing.com/kb/fastapi-vs-flask-a-detailed-comparison)
- [How To Serve Flask Applications with Gunicorn and Nginx on Ubuntu 22.04](https://www.digitalocean.com/community/tutorials/how-to-serve-flask-applications-with-gunicorn-and-nginx-on-ubuntu-22-04)

## Logger
- [logger](https://replit.com/@pgorecki/request-logger?v=1#main.py)

## Nginx
- [Deploy Docker Image to AWS-EKS](https://medium.com/@sejalmaniyar9/deploying-a-docker-image-to-aws-eks-504f4fec6fee)


# Author

---

- Rohtash Lakra
