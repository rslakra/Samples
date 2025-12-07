# Spring Boot Example

A simple Spring Boot 3.5.7 application demonstrating RESTful web services with H2 database integration.

## Features

- Spring Boot 3.5.7
- RESTful API endpoints
- H2 in-memory/file database integration
- Spring Data JPA
- H2 Console for database management
- Lombok for cleaner code

## Prerequisites

- Java 21
- Maven 3.6+

## Getting Started

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Home

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Welcome page |

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create a new user |
| PUT | `/api/users/{id}` | Update an existing user |
| DELETE | `/api/users/{id}` | Delete a user |

## H2 Database

### Access H2 Console

- **URL**: `http://localhost:8080/h2`
- **JDBC URL**: `jdbc:h2:file:~/Downloads/H2DB/SpringBootExample`
- **Username**: `sa`
- **Password**: (empty)

### Database Configuration

The application uses H2 file-based database. The database file is stored at:
```
~/Downloads/H2DB/SpringBootExample.mv.db
```

## Usage Examples

### Create a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER"
  }'
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

### Get All Users

```bash
curl http://localhost:8080/api/users
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER"
  }
]
```

### Get User by ID

```bash
curl http://localhost:8080/api/users/1
```

### Update User

```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john@example.com",
    "role": "ADMIN"
  }'
```

### Delete User

```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/rslakra/springbootexample/
│   │       ├── controller/          # REST controllers
│   │       ├── model/               # Entity classes
│   │       ├── repository/          # JPA repositories
│   │       └── DemoApplication.java  # Main application class
│   └── resources/
│       └── application.properties   # Configuration
└── test/
    └── java/                        # Test classes
```

## Configuration

Key settings in `application.properties`:

- **Server Port**: 8080
- **Database**: H2 file-based (`~/Downloads/H2DB/SpringBootExample`)
- **JPA**: Hibernate with `create-drop` strategy
- **H2 Console**: Enabled at `/h2`

## Technology Stack

- **Spring Boot**: 3.5.7
- **Java**: 21
- **Database**: H2
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven

## Reference Documentation

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/)
- [Spring Web](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data.sql.jpa-and-spring-data)

## Guides

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)


# Author

---

- Rohtash Lakra
