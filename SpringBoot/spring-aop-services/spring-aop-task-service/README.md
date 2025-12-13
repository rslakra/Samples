# Spring AOP Task Service

A Spring Boot sample application demonstrating a **Task Management Service** with AOP (Aspect-Oriented Programming), JPA persistence, and RESTful APIs.

## Overview

This application demonstrates how to:
- Build a RESTful task management API
- Use Spring Data JPA for persistence
- Implement role-based access control
- Apply AOP for cross-cutting concerns
- Create web controllers with Thymeleaf templates

## Features

- **Task Management**: Create, read, update, delete tasks
- **User Management**: User accounts with roles
- **Role-Based Access**: Admin and user role support
- **Todo Lists**: Personal todo items per user
- **RESTful APIs**: Full CRUD operations via REST
- **Web Interface**: Thymeleaf-based UI
- **H2 Database**: In-memory database for development
- **AOP Integration**: Cross-cutting concern handling

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Project Structure

```
spring-aop-task-service/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/rslakra/iws/taskservice/
│       │       ├── account/
│       │       │   ├── controller/
│       │       │   │   ├── RoleController.java        # REST API
│       │       │   │   ├── UserController.java        # REST API
│       │       │   │   └── web/
│       │       │   │       ├── RoleWebController.java # Web UI
│       │       │   │       └── UserWebController.java # Web UI
│       │       │   ├── filter/
│       │       │   │   ├── RoleFilter.java
│       │       │   │   └── UserFilter.java
│       │       │   ├── persistence/
│       │       │   │   ├── entity/
│       │       │   │   │   ├── Role.java
│       │       │   │   │   ├── User.java
│       │       │   │   │   ├── Person.java
│       │       │   │   │   ├── Task.java
│       │       │   │   │   └── Todo.java
│       │       │   │   └── repository/
│       │       │   └── service/
│       │       │       └── impl/
│       │       ├── task/
│       │       │   ├── controller/
│       │       │   │   ├── TaskController.java
│       │       │   │   └── TodoController.java
│       │       │   ├── filter/
│       │       │   │   ├── TaskFilter.java
│       │       │   │   └── TodoFilter.java
│       │       │   └── service/
│       │       └── TaskServiceApplication.java
│       └── resources/
│           ├── templates/                    # Thymeleaf templates
│           └── application.properties
├── pom.xml
├── buildMaven.sh
├── runMaven.sh
└── README.md
```

## Key Components

### 1. Domain Entities

| Entity | Description |
|--------|-------------|
| `User` | User account with credentials |
| `Role` | User roles (ADMIN, USER) |
| `Person` | Person details |
| `Task` | Task items with priority |
| `Todo` | Todo items linked to users |

### 2. REST Controllers

| Controller | Endpoints |
|------------|-----------|
| `RoleController` | `/roles/**` - Role CRUD operations |
| `UserController` | `/users/**` - User CRUD operations |
| `TaskController` | `/tasks/**` - Task CRUD operations |
| `TodoController` | `/todos/**` - Todo CRUD operations |

### 3. Web Controllers
- `RoleWebController` - Web UI for roles
- `UserWebController` - Web UI for users

### 4. Filters
Type-safe filters extending `DefaultFilter<T>` for query parameters:
- `RoleFilter<Role>` - Role query filters
- `UserFilter<User>` - User query filters
- `TaskFilter<Task>` - Task query filters
- `TodoFilter<Todo>` - Todo query filters

## Building the Project

### Using the build script:
```bash
./buildMaven.sh
```

### Using Maven directly:
```bash
mvn clean install
```

### Skip checkstyle (if network issues):
```bash
mvn clean install -Dcheckstyle.skip=true
```

## Running the Application

### Option 1: Using the run script (Recommended)
```bash
./runMaven.sh
```

### Option 2: Using Maven Spring Boot plugin
```bash
mvn spring-boot:run
```

### Option 3: Running the JAR file
```bash
java -jar target/spring-aop-task-service.jar
```

### Option 4: From IDE
Run the `main` method in `TaskServiceApplication.java`

## API Endpoints

### Roles API
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/roles` | List all roles |
| GET | `/roles/{id}` | Get role by ID |
| POST | `/roles` | Create new role |
| PUT | `/roles/{id}` | Update role |
| DELETE | `/roles/{id}` | Delete role |

### Users API
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users` | List all users |
| GET | `/users/{id}` | Get user by ID |
| POST | `/users` | Create new user |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

### Tasks & Todos
Similar CRUD endpoints available for `/tasks/**` and `/todos/**`

## Testing

Run the unit tests:
```bash
mvn test
```

## Dependencies

- **Spring Boot Starter Web**: Web application support
- **Spring Boot Starter Data JPA**: JPA persistence
- **Spring Boot Starter Thymeleaf**: Template engine
- **Spring Boot Starter AOP**: AOP support
- **H2 Database**: In-memory database
- **Liquibase**: Database migrations
- **AppSuite Spring**: Common utilities (`appsuite-spring`)
- **Spring Boot Starter Test**: Testing framework

## Migration Notes (Spring Boot 2.x to 3.x)

This project has been migrated to Spring Boot 3.x:

1. **Jakarta EE**: Migrated from `javax.persistence` to `jakarta.persistence`
2. **Java 21**: Updated to Java 21
3. **Filter Generics**: Updated filters to use typed `DefaultFilter<T>`
4. **ServiceOperation**: Renamed from `Operation` to `ServiceOperation`

## References

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)
* [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
* [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)

## Authors

- Rohtash Lakra
