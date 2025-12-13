# Spring AOP Service

A Spring Boot sample application demonstrating comprehensive **AOP (Aspect-Oriented Programming)** functionality. This project showcases various AOP advice types including Before, After, Around, and annotation-based pointcuts using AspectJ.

## Overview

This application demonstrates how to:
- Implement multiple AOP advice types (Before, After, Around)
- Create custom annotations for AOP pointcuts
- Use AspectJ for compile-time and runtime weaving
- Configure AOP via XML and annotations
- Intercept method execution across service layers

## Features

- **Multiple Aspect Types**: Demonstrates various aspect implementations
- **Custom Annotation**: `@Loggable` - A marker annotation for AOP interception
- **Before Advice**: Execute logic before method invocation
- **After Advice**: Execute logic after method completion
- **Around Advice**: Full control over method execution
- **AspectJ Integration**: Compile-time weaving support
- **XML & Annotation Config**: Both configuration styles supported

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Project Structure

```
spring-aop-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/rslakra/aopservice/
│   │   │       ├── aspect/
│   │   │       │   ├── EmployeeAfterAspect.java      # After advice
│   │   │       │   ├── EmployeeAnnotationAspect.java # Annotation-based
│   │   │       │   ├── EmployeeAroundAspect.java     # Around advice
│   │   │       │   ├── EmployeeAspect.java           # Basic aspect
│   │   │       │   ├── EmployeeAspectJoinPoint.java  # JoinPoint demo
│   │   │       │   ├── EmployeeAspectPointcut.java   # Pointcut demo
│   │   │       │   ├── EmployeeXMLConfigAspect.java  # XML config
│   │   │       │   └── Loggable.java                 # Custom annotation
│   │   │       ├── model/
│   │   │       │   └── Employee.java                 # Employee model
│   │   │       ├── service/
│   │   │       │   └── EmployeeService.java          # Employee service
│   │   │       └── AopService.java                   # Main application
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
│       └── java/
│           └── com/rslakra/aopservice/
│               └── AopServiceTest.java
├── pom.xml
├── buildMaven.sh
├── runMaven.sh
└── README.md
```

## Key Components

### 1. Custom Annotation
`@Loggable` - A runtime annotation for marking methods to be intercepted by AOP.

### 2. Aspect Classes

| Aspect | Description |
|--------|-------------|
| `EmployeeAspect` | Basic aspect demonstrating fundamental AOP concepts |
| `EmployeeAfterAspect` | After advice - executes after method completion |
| `EmployeeAroundAspect` | Around advice - full method interception |
| `EmployeeAnnotationAspect` | Annotation-based pointcuts |
| `EmployeeAspectJoinPoint` | JoinPoint usage demonstration |
| `EmployeeAspectPointcut` | Pointcut expressions demo |
| `EmployeeXMLConfigAspect` | XML-based configuration |

### 3. Service Layer
`EmployeeService` - Business logic layer with AOP-intercepted methods.

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

### Option 3: Running the WAR file
```bash
java -jar target/spring-aop-service-0.0.1.war
```

### Option 4: From IDE
Run the `main` method in `AopService.java`

## Usage Example

To use the `@Loggable` annotation in your own methods:

```java
@Service
public class MyService {
    
    @Loggable
    public void myMethod() {
        // Your method logic here
    }
}
```

## Testing

Run the unit tests:
```bash
mvn test
```

## Dependencies

- **Spring Boot Starter AOP**: Provides AOP support
- **Spring Boot Starter Web**: Web application support
- **AspectJ Weaver**: Runtime weaving support
- **AspectJ RT**: AspectJ runtime
- **AspectJ Tools**: Compile-time weaving tools
- **Jakarta Servlet API**: Servlet support for Spring Boot 3.x
- **Spring Boot Starter Test**: Testing framework

## Migration Notes (Spring Boot 2.x to 3.x)

This project has been migrated to Spring Boot 3.x:

1. **Java 21**: Updated to Java 21 for latest features
2. **Jakarta EE**: Migrated from `javax.servlet` to `jakarta.servlet`
3. **AspectJ 1.9.25**: Updated for Java 21 compatibility

## References

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)
* [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
* [AspectJ Documentation](https://www.eclipse.org/aspectj/)

## Authors

- Rohtash Lakra
