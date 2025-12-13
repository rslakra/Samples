# Spring Boot Resource Server

A Spring Boot sample application demonstrating **OAuth2 Resource Server** functionality with Spring Security 6.x. This project shows how to configure a secure REST API with role-based access control.

## Overview

This application demonstrates how to:
- Configure Spring Security for web applications
- Implement role-based access control (RBAC)
- Set up in-memory user authentication with password encoding
- Create secure REST endpoints
- Configure OAuth2 Resource Server for JWT token validation

## Features

- **Spring Security 6.x**: Modern component-based security configuration
- **Role-Based Access Control**: Restrict endpoints based on user roles (ADMIN)
- **Password Encoding**: BCrypt password encoder for secure password storage
- **Form Login**: Built-in form-based authentication
- **OAuth2 Resource Server**: JWT token authentication support

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Project Structure

```
springboot-resource-server/
├── src/
│   └── main/
│       └── java/
│           └── com/rslakra/resourceserver/
│               ├── config/
│               │   ├── AuthorizationServerConfig.java   # OAuth2/JWT configuration
│               │   └── EmployeeSecurityConfiguration.java # Security configuration
│               ├── controllers/
│               │   └── EmployeeController.java          # REST controller
│               ├── model/
│               │   └── Employee.java                    # Employee model
│               └── ResourceServerApplication.java       # Main application
├── pom.xml
├── buildMaven.sh
├── runMaven.sh
└── README.md
```

## Key Components

### 1. Security Configuration
`EmployeeSecurityConfiguration` - Configures Spring Security with:
- `SecurityFilterChain` - Defines URL-based authorization rules
- `UserDetailsService` - In-memory user with ADMIN role
- `PasswordEncoder` - BCrypt password encoding
- Form login and logout support

### 2. OAuth2 Resource Server Configuration
`AuthorizationServerConfig` - Configures JWT authentication:
- `JwtAuthenticationConverter` - Extracts authorities from JWT tokens
- Supports integration with external authorization servers

### 3. REST Controller
`EmployeeController` - Provides a secured REST endpoint:
- `/user/getEmployees` - Returns list of employees (requires ADMIN role)

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
java -jar target/springboot-resource-server.jar
```

### Option 4: From IDE
Run the `main` method in `ResourceServerApplication.java`

## Testing the Application

### Access the Application

1. **Open browser**: Navigate to `http://localhost:8080/`
2. **Login**: Use the following credentials:
   - **Username**: `admin`
   - **Password**: `admin`

### Test Endpoints

| Endpoint | Access | Description |
|----------|--------|-------------|
| `/` | Public | Home page (no authentication required) |
| `/user/getEmployees` | ADMIN only | Returns JSON list of employees |

### Example API Request

After logging in, access the employees list:
```bash
# Using browser after form login
http://localhost:8080/user/getEmployees
```

Expected response:
```json
[
  {
    "empId": "emp1",
    "empName": "emp1"
  }
]
```

## Security Configuration Details

### URL Authorization Rules

| URL Pattern | Access Level |
|-------------|--------------|
| `/` | Permit All |
| `/resources/**` | Ignored (static resources) |
| `/user/getEmployees` | ADMIN role required |
| All other URLs | Authenticated users |

### Default User

| Username | Password | Role |
|----------|----------|------|
| admin | admin | ADMIN |

## Dependencies

- **Spring Boot Starter Web**: Web application support
- **Spring Boot Starter Security**: Security framework
- **Spring Boot Starter OAuth2 Resource Server**: OAuth2/JWT support
- **Spring Boot Starter Test**: Testing framework

## Migration Notes (Spring Boot 2.x to 3.x)

This project has been migrated from Spring Boot 2.x to 3.x with the following changes:

1. **Deprecated `WebSecurityConfigurerAdapter`** → Component-based configuration with `SecurityFilterChain` bean
2. **`antMatchers()`** → `requestMatchers()`
3. **Old Spring Security OAuth2** → `spring-boot-starter-oauth2-resource-server`
4. **Password encoding** required for in-memory authentication

## References

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)
* [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
* [Spring Security OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
* [Spring Authorization Server](https://spring.io/projects/spring-authorization-server)

## Authors

- Rohtash Lakra
