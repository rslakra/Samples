# Spring Boot Service

A Spring Boot 3.5.7 application demonstrating RESTful web services with H2 database integration, Spring Data REST, and Thymeleaf templates.

## Features

- Spring Boot 3.5.7
- RESTful API endpoints
- H2 file-based database integration
- Spring Data JPA
- Spring Data REST
- Spring HATEOAS
- Thymeleaf templates
- H2 Console for database management
- Lombok for cleaner code
- Support for both Maven and Gradle builds

## Prerequisites

- Java 21
- Maven 3.6+ (or Gradle 7+)

## Getting Started

### Build the Project

**Using Maven:**
```bash
mvn clean install
```

Or use the convenience script:
```bash
./buildMaven.sh
```

**Using Gradle:**
```bash
./gradlew build
```

Or use the convenience script:
```bash
./buildGradle.sh
```

### Run the Application

**Using Maven:**
```bash
mvn spring-boot:run
```

Or use the convenience script:
```bash
./runMaven.sh
```

**Using Gradle:**
```bash
./gradlew bootRun
```

Or use the convenience script:
```bash
./runGradle.sh
```

The application will start on `http://localhost:8080`

### Convenience Scripts

The project includes shell scripts for easier build and run operations:

- **`buildMaven.sh`**: Builds the project with Maven, automatically calculating version from Git commit count
- **`buildGradle.sh`**: Builds the project with Gradle, automatically calculating version from Git commit count
- **`runMaven.sh`**: Runs the application using Maven
- **`runGradle.sh`**: Runs the application using Gradle

All scripts skip tests during execution for faster builds and runs.

## API Endpoints

### Home

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page |
| GET | `/rest/`, `/rest/index`, `/rest/home` | REST home endpoint |

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create a new user |
| PUT | `/api/users/{id}` | Update an existing user |
| DELETE | `/api/users/{id}` | Delete a user |

### Images

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/images/{filename}` | Get image by filename |

## H2 Database

### Access H2 Console

- **URL**: `http://localhost:8080/h2`
- **JDBC URL**: `jdbc:h2:file:~/Downloads/H2DB/SpringBootService`
- **Username**: `sa`
- **Password**: (empty)

### Database Configuration

The application uses H2 file-based database. The database file is stored at:
```
~/Downloads/H2DB/SpringBootService.mv.db
```

**Note**: The H2 database URL uses a simple file-based connection. The incompatible options `AUTO_SERVER=TRUE` and `DB_CLOSE_ON_EXIT=FALSE` cannot be used together. If you need multiple connections, you can use `AUTO_SERVER=TRUE` alone, or `DB_CLOSE_ON_EXIT=FALSE` alone, but not both.

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
│   │   └── com/rslakra/springbootservice/
│   │       ├── controller/          # REST controllers
│   │       │   └── rest/            # REST-specific controllers
│   │       ├── model/                # Entity classes
│   │       ├── repository/           # JPA repositories
│   │       └── SpringBootServiceApplication.java
│   └── resources/
│       ├── application.yaml         # Configuration
│       ├── templates/               # Thymeleaf templates
│       └── static/                   # Static resources
└── test/
    └── java/                         # Test classes
```

## Configuration

Key settings in `application.yaml`:

- **Server Port**: 8080
- **Database**: H2 file-based (`~/Downloads/H2DB/SpringBootService`)
- **JPA**: Hibernate with `create-drop` strategy
- **H2 Console**: Enabled at `/h2`
- **Thymeleaf**: Enabled for template rendering

**Test Configuration**: Tests use an in-memory H2 database (`jdbc:h2:mem:SpringBootServiceTestDB`) configured in `application-test.yaml` to avoid file system dependencies during testing.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Java**: 21
- **Database**: H2 (file-based), MySQL (optional)
- **ORM**: Spring Data JPA / Hibernate
- **REST**: Spring Data REST, Spring HATEOAS
- **Templates**: Thymeleaf
- **Build Tools**: Maven, Gradle

## Build Tools

This project supports both Maven and Gradle with synchronized dependencies:

- **Maven**: Use `pom.xml` and `mvn` commands or convenience scripts (`buildMaven.sh`, `runMaven.sh`)
- **Gradle**: Use `build.gradle` and `./gradlew` commands or convenience scripts (`buildGradle.sh`, `runGradle.sh`)

Both build files (`pom.xml` and `build.gradle`) are kept in sync with the same dependencies and versions:
- Spring Boot 3.5.7
- Spring Data JPA
- Spring Data REST
- Spring HATEOAS
- Thymeleaf
- H2 Database
- MySQL Connector (optional)
- Lombok

## Reference Documentation

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data REST](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data.sql.jpa-and-spring-data.rest)
- [Spring HATEOAS](https://docs.spring.io/spring-hateoas/docs/current/reference/html/)

## Guides

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)


# Author

---

- Rohtash Lakra
