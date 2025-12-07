# Spring Boot Services

The ```Spring Boot Services``` project contains various example services demonstrating Spring Boot 3.5.7 features and integrations.

## Overview

This directory contains multiple Spring Boot applications showcasing different aspects of Spring Boot development, including RESTful APIs, authentication, database integration, web views, and more.

## Services

### Core Services

- **[Email Service](./email-service/README.md)** - A comprehensive email service application with user authentication, password management, and email functionality using Thymeleaf templates.

- **[JWT Authentication Service](./jwt-authentication/README.md)** - JWT-based authentication service with role-based access control (RBAC), supporting USER, ADMIN, MANAGER, and GUEST roles.

- **[Spring Boot Example](./springboot-example/README.md)** - A simple Spring Boot application demonstrating RESTful web services with H2 database integration and Spring Data JPA.

- **[Spring Boot Service](./springboot-service/README.md)** - A Spring Boot service demonstrating RESTful APIs with H2 database, Spring Data REST, Spring HATEOAS, and Thymeleaf templates. Supports both Maven and Gradle builds.

- **[Spring Boot JSP Service](./springboot-service-jsp/README.md)** - A Spring Boot application demonstrating JavaServer Pages (JSP) integration with H2 database, featuring a book management system with modern UI components.

### AOP Services

- **[Spring AOP Services](./spring-aop-services/README.md)** - Spring AOP examples demonstrating various aspects and advice types (before, around, etc.).

### Resource Servers

- **[Spring Resource Servers](./spring-resource-servers/)** - OAuth2 resource server examples for securing REST APIs.

## Common Features

All services in this directory share common characteristics:

- **Spring Boot Version**: 3.5.7
- **Java Version**: 21
- **Build Tools**: Maven (and Gradle for some services)
- **Database**: H2 (file-based or in-memory) with optional MySQL support
- **Testing**: Comprehensive unit and integration tests

## Build All Services

To build all Maven-based services in this directory, use the convenience script:

```bash
./buildMaven.sh
```

This script automatically discovers and builds all Maven projects within the SpringBoot directory.

## Technology Stack

- **Spring Boot**: 3.5.7
- **Java**: 21
- **Spring Framework**: 6.x
- **Spring Security**: 6.x
- **Spring Data JPA**: 3.x
- **H2 Database**: Latest
- **Build Tools**: Maven 3.6+, Gradle 7+
- **View Technologies**: Thymeleaf, JSP
- **Lombok**: For cleaner code

## Project Structure

```
SpringBoot/
├── email-service/              # Email service with authentication
├── jwt-authentication/         # JWT authentication service
├── springboot-example/         # Simple RESTful API example
├── springboot-service/         # RESTful service with Data REST
├── springboot-service-jsp/     # JSP-based book management
├── spring-aop-services/        # AOP examples
├── spring-resource-servers/    # OAuth2 resource servers
├── buildMaven.sh               # Build script for all services
└── README.md                   # This file
```

## Getting Started

1. Navigate to the specific service directory
2. Read the service-specific README for detailed instructions
3. Build and run the service using Maven or Gradle
4. Access the service endpoints as documented in each service's README

## Reference

- [Email Service](./email-service/README.md)
- [JWT Authentication Service](./jwt-authentication/README.md)
- [Spring Boot Example](./springboot-example/README.md)
- [Spring Boot Service](./springboot-service/README.md)
- [Spring Boot JSP Service](./springboot-service-jsp/README.md)
- [Spring AOP Services](./spring-aop-services/README.md)

## Reference Documentation

- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [H2 Database](https://www.h2database.com/html/main.html)

## Author

- Rohtash Lakra
