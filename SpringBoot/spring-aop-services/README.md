# Spring AOP Services

A collection of Spring Boot sample applications demonstrating **AOP (Aspect-Oriented Programming)** functionality with various advice types and use cases.

## Overview

This directory contains multiple AOP-focused projects showcasing different aspects of Spring AOP and AspectJ integration.

## Projects

| Project | Description |
|---------|-------------|
| [spring-aop-around-advice](./spring-aop-around-advice/) | Demonstrates `@Around` advice for method execution timing |
| [spring-aop-before-advice](./spring-aop-before-advice/) | Demonstrates `@Before` advice with H2 database integration |
| [spring-aop-service](./spring-aop-service/) | Comprehensive AOP demo with multiple advice types |
| [spring-aop-task-service](./spring-aop-task-service/) | Task management service with AOP, JPA, and REST APIs |

## Features

All projects demonstrate:
- **Spring Boot 3.5.7**: Latest Spring Boot with Java 21
- **Spring AOP**: Aspect-oriented programming with Spring
- **AspectJ Integration**: Advanced AOP features
- **Custom Annotations**: Marker annotations for pointcuts
- **Multiple Advice Types**: Before, After, Around advice

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Building All Projects

```bash
# Build spring-aop-around-advice
cd spring-aop-around-advice && ./buildMaven.sh && cd ..

# Build spring-aop-before-advice
cd spring-aop-before-advice && ./buildMaven.sh && cd ..

# Build spring-aop-service
cd spring-aop-service && ./buildMaven.sh && cd ..

# Build spring-aop-task-service
cd spring-aop-task-service && ./buildMaven.sh && cd ..
```

## Running Projects

Each project can be run independently:

```bash
# Run spring-aop-around-advice
cd spring-aop-around-advice && ./runMaven.sh

# Run spring-aop-before-advice (port 8080)
cd spring-aop-before-advice && ./runMaven.sh

# Run spring-aop-service
cd spring-aop-service && ./runMaven.sh

# Run spring-aop-task-service (port 9090)
cd spring-aop-task-service && ./runMaven.sh
```

## AOP Advice Types

| Advice Type | Description | Projects |
|-------------|-------------|----------|
| `@Before` | Executes before method invocation | spring-aop-before-advice, spring-aop-service |
| `@After` | Executes after method completion | spring-aop-service |
| `@Around` | Full control over method execution | spring-aop-around-advice, spring-aop-service |
| `@AfterReturning` | Executes after successful return | spring-aop-service |
| `@AfterThrowing` | Executes after exception thrown | spring-aop-service |

## Migration Notes (Spring Boot 2.x to 3.x)

All projects have been migrated to Spring Boot 3.x:

1. **Java 21**: Updated to Java 21 for latest features
2. **Jakarta EE**: Migrated from `javax.*` to `jakarta.*` packages
3. **Spring Security 6.x**: Updated security configuration patterns
4. **AspectJ 1.9.25**: Updated for Java 21 compatibility

## References

* [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
* [AspectJ Documentation](https://www.eclipse.org/aspectj/)
* [Spring Boot Maven Plugin Reference](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)

## Authors

- Rohtash Lakra
