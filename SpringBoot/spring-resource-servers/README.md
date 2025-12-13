# Spring Resource Servers

A collection of Spring Boot sample applications demonstrating **OAuth2 Resource Server** functionality with Spring Security 6.x.

## Overview

This directory contains multiple resource server implementations showcasing different security configurations and patterns for securing REST APIs.

## Projects

| Project | Description |
|---------|-------------|
| [springboot-resource-server](./springboot-resource-server/) | Basic resource server with form login and role-based access control |
| [springboot-resource-server-access](./springboot-resource-server-access/) | Resource server with enhanced security configuration |

## Features

All projects demonstrate:
- **Spring Security 6.x**: Modern component-based security configuration
- **Role-Based Access Control**: Restrict endpoints based on user roles
- **Password Encoding**: BCrypt password encoder for secure password storage
- **Form Login**: Built-in form-based authentication
- **OAuth2 Resource Server Support**: JWT token authentication (optional)

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Building All Projects

```bash
# Build springboot-resource-server
cd springboot-resource-server && ./buildMaven.sh && cd ..

# Build springboot-resource-server-access
cd springboot-resource-server-access && ./buildMaven.sh && cd ..
```

## Running Projects

Each project can be run independently:

```bash
# Run springboot-resource-server (default port 8080)
cd springboot-resource-server && ./runMaven.sh

# Run springboot-resource-server-access (default port 8080)
cd springboot-resource-server-access && ./runMaven.sh
```

## Default Credentials

All projects use the same default credentials:

| Username | Password | Role |
|----------|----------|------|
| admin | admin | ADMIN |

## Migration Notes (Spring Boot 2.x to 3.x)

These projects have been migrated from Spring Boot 2.x to 3.x:

1. **`WebSecurityConfigurerAdapter`** → `SecurityFilterChain` bean
2. **`antMatchers()`** → `requestMatchers()`
3. **Old Spring Security OAuth2** → `spring-boot-starter-oauth2-resource-server`
4. **`@EnableResourceServer`** → `SecurityFilterChain` with `oauth2ResourceServer()` DSL
5. **Password encoding** required for in-memory authentication

## References

* [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
* [Spring Security OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
* [Spring Authorization Server](https://spring.io/projects/spring-authorization-server)
* [Spring Boot Maven Plugin Reference](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)

## Authors

- Rohtash Lakra
