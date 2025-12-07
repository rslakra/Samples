# Spring Services

The ```Spring Services``` project contains various example services demonstrating Spring Framework features and different configuration approaches.

## Overview

This directory contains multiple Spring Framework applications showcasing different Spring configuration methods, including XML-based, Java-based, annotation-based, and core Spring concepts.

## Services

### Configuration Examples

- **Spring XML** (`spring-xml/`) - Spring Framework application configured using XML-based configuration (`applicationContext.xml`). Demonstrates traditional Spring XML bean definitions and dependency injection.

- **Spring Java** (`spring-java/`) - Spring Framework application configured using Java-based configuration (`@Configuration` classes). Demonstrates modern Java configuration approach with `AppConfig.java`.

- **Spring Annotation** (`spring-annotation/`) - Spring Framework application configured using annotation-based configuration. Demonstrates `@Component`, `@Service`, `@Repository` annotations and component scanning.

- **Spring Core** (`spring-core/`) - Spring Framework core concepts demonstration. Shows fundamental Spring features including dependency injection, inversion of control (IoC), and application context.

## Common Features

All services in this directory share common characteristics:

- **Spring Framework**: Latest version
- **Java Version**: 21
- **Build Tool**: Maven 3.6+
- **Architecture**: Demonstrates Customer service with repository and service layers
- **Configuration**: Different configuration approaches (XML, Java, Annotations)

## Project Structure

```
Spring/
├── spring-xml/              # XML-based configuration
├── spring-java/             # Java-based configuration
├── spring-annotation/       # Annotation-based configuration
├── spring-core/             # Core Spring concepts
└── README.md                # This file
```

## Getting Started

1. Navigate to the specific service directory
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application using the convenience script:
   ```bash
   ./runMaven.sh
   ```
   Or directly:
   ```bash
   mvn exec:java -Dexec.mainClass="com.rslakra.spring[service].CustomerApplication"
   ```

## Technology Stack

- **Spring Framework**: Latest
- **Java**: 21
- **Build Tool**: Maven 3.6+
- **Code Quality**: Checkstyle

## Reference

- [Spring XML Configuration](./spring-xml/)
- [Spring Java Configuration](./spring-java/)
- [Spring Annotation Configuration](./spring-annotation/)
- [Spring Core](./spring-core/)

## Reference Documentation

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Spring Core Features](https://docs.spring.io/spring-framework/reference/core.html)
- [Spring Configuration](https://docs.spring.io/spring-framework/reference/core/beans.html)

## Author

- Rohtash Lakra
