# Spring AOP Before Advice

A Spring Boot sample application demonstrating **AOP (Aspect-Oriented Programming) Before Advice** functionality. This project shows how to use Spring AOP to intercept method execution before it runs using the `@Before` advice type.

## Overview

This application demonstrates how to:
- Create custom annotations for AOP pointcuts
- Implement `@Before` advice to intercept method execution before it runs
- Access HTTP request context in AOP aspects
- Validate and log method invocations with request information
- Use Spring AOP with AspectJ annotations in a web application

## Features

- **Custom Annotation**: `@Loggable` - A marker annotation with `localOnly` option to identify methods for AOP interception
- **Before Advice**: Intercepts method execution before it runs, allowing logging and validation
- **Request Context Access**: Demonstrates accessing HTTP request information from within AOP aspects
- **REST API**: Provides REST endpoints to demonstrate the AOP functionality
- **Localhost Validation**: Optional `localOnly` flag to restrict access to localhost requests
- **H2 Database**: File-based database with web console for database management
- **JPA/Hibernate**: Automatic schema management and SQL logging

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Project Structure

```
spring-aop-before-advice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/rslakra/springbootsamples/aopbeforeadvice/
│   │   │       ├── aspect/
│   │   │       │   ├── Loggable.java                    # Custom annotation
│   │   │       │   └── LoggableAnnotationAspect.java   # AOP Before Advice
│   │   │       ├── controller/
│   │   │       │   └── AdviceController.java           # REST Controller
│   │   │       ├── model/
│   │   │       │   └── Advice.java                     # Domain model
│   │   │       ├── service/
│   │   │       │   └── AdviceService.java              # Service layer
│   │   │       └── SpringAOPBeforeAdvice.java           # Main application
│   │   └── resources/
│   │       ├── application.properties
│   │       └── logback.xml
│   └── test/
│       └── java/
│           └── com/rslakra/springbootsamples/aopbeforeadvice/
│               ├── AdviceControllerTest.java
│               └── SpringAOPBeforeAdviceTest.java
├── pom.xml
├── buildMaven.sh
└── runMaven.sh
```

## Key Components

### 1. Custom Annotation
`@Loggable` - A runtime annotation that can be applied to methods with an optional `localOnly` parameter:
- `localOnly = true`: Restricts access to localhost requests only
- `localOnly = false` (default): Allows all requests

### 2. AOP Aspect
`LoggableAnnotationAspect` - Contains the `@Before` advice that:
- Intercepts methods annotated with `@Loggable`
- Logs method name and arguments
- Accesses HTTP request context
- Validates localhost access when `localOnly = true`
- Performs request hash validation

### 3. REST Controller
`AdviceController` - Provides REST endpoints:
- `GET /rest/advices` - Get all advices (localOnly = true)
- `GET /rest/advices/filter?id={id}` - Get advice by ID (localOnly = true)
- `POST /rest/advices` - Create a new advice
- `POST /rest/advices/batch` - Create multiple advices

## Building the Project

### Using the build script:
```bash
./buildMaven.sh
```

### Using Maven directly:
```bash
mvn clean install
```

This will:
- Compile the source code
- Run checkstyle validation
- Execute unit tests
- Package the application as a JAR file

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
# First build the project
./buildMaven.sh

# Then run the JAR
java -jar target/spring-aop-before-advice-0.0.1.jar
```

### Option 4: From IDE
Run the `main` method in `SpringAOPBeforeAdvice.java`

## Configuration

The application uses `application.properties` for configuration. Key properties:

### Application Settings
- `restPrefix = /rest` - Base path for REST endpoints
- `spring.jpa.open-in-view = false` - Disables JPA open-in-view

### H2 Database Configuration
- `spring.datasource.url = jdbc:h2:file:~/Downloads/H2DB/SpringAOPBeforeAdvice;AUTO_SERVER=TRUE` - H2 file-based database
- `spring.datasource.username = sa` - Database username
- `spring.datasource.password =` - Database password (empty)
- `spring.jpa.database-platform = org.hibernate.dialect.H2Dialect` - Hibernate dialect
- `spring.jpa.show-sql = true` - Enable SQL logging
- `spring.jpa.hibernate.ddl-auto = update` - Auto-update database schema

### H2 Console
- `spring.h2.console.enabled = true` - Enable H2 console
- `spring.h2.console.path = /h2` - H2 console path
- `spring.h2.console.settings.web-allow-others = true` - Allow remote access

### Server Configuration
To configure server port, uncomment and modify:
```properties
server.port = 8080
server.servlet.contextPath = /spring-aop-before-advice
```

## API Endpoints

### Create Advice
```bash
curl --location 'http://localhost:8080/rest/advices' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "name": "Rohtash",
    "message": "Lakra"
}'
```

### Get All Advices
```bash
curl --location 'http://localhost:8080/rest/advices'
```

### Get Advice by ID
```bash
curl --location 'http://localhost:8080/rest/advices/filter?id=1'
```

### Create Multiple Advices
```bash
curl --location 'http://localhost:8080/rest/advices/batch' \
--header 'Content-Type: application/json' \
--data '[
    {"id": 1, "name": "Rohtash", "message": "Lakra"},
    {"id": 2, "name": "John", "message": "Doe"}
]'
```

## H2 Database Console

The application includes H2 database console for database management and inspection.

### Accessing H2 Console

1. Start the application:
   ```bash
   ./runMaven.sh
   ```

2. Open your browser and navigate to:
   ```
   http://localhost:8080/h2
   ```

3. Login with the following credentials:
   - **JDBC URL**: `jdbc:h2:file:~/Downloads/H2DB/SpringAOPBeforeAdvice`
   - **Username**: `sa`
   - **Password**: (leave empty)

### H2 Console Features

- View database tables and schema
- Execute SQL queries
- Browse table data
- Monitor database operations

**Note**: The database file is stored at `~/Downloads/H2DB/SpringAOPBeforeAdvice.mv.db` (file-based storage).

## How It Works

1. The `@Loggable` annotation is applied to controller methods
2. Spring AOP detects the annotation and applies the `@Before` advice
3. Before method execution: The aspect:
   - Logs the method name and arguments
   - Accesses the HTTP request from Spring's RequestContextHolder
   - Validates localhost access if `localOnly = true`
   - Performs request hash validation
4. The original method executes after the advice completes

## Usage Example

To use the AOP functionality in your own methods, simply annotate them:

```java
@RestController
public class MyController {
    
    @Loggable(localOnly = true)
    @GetMapping("/secure")
    public String secureEndpoint() {
        // Only accessible from localhost
        return "Secure data";
    }
    
    @Loggable
    @GetMapping("/public")
    public String publicEndpoint() {
        // Accessible from anywhere
        return "Public data";
    }
}
```

## Testing

Run the unit tests:
```bash
mvn test
```

The project includes:
- `SpringAOPBeforeAdviceTest` - Tests Spring context loading
- `AdviceControllerTest` - Tests the REST controller with AOP interception

## Dependencies

- **Spring Boot Starter Web**: Provides web and REST support
- **Spring Boot Starter AOP**: Provides AOP support
- **Spring Boot Starter Data JPA**: Provides JPA and Hibernate support
- **H2 Database**: Embedded in-memory/file-based database for development
- **Spring Boot Starter Test**: Provides testing framework
- **AppSuite Core & Spring**: Internal utility libraries

### Database

The application uses **H2 Database** (file-based) for data persistence:
- Database location: `~/Downloads/H2DB/SpringAOPBeforeAdvice.mv.db`
- H2 Console enabled for database management
- JPA/Hibernate configured for automatic schema updates

## References

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)
* [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
* [AspectJ Documentation](https://www.eclipse.org/aspectj/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/#build-image)

## Authors

- Rohtash Lakra
