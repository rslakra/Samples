# Spring AOP Around Advice

A Spring Boot sample application demonstrating **AOP (Aspect-Oriented Programming) Around Advice** functionality. This project shows how to use Spring AOP to intercept method execution and measure execution time using the `@Around` advice type.

## Overview

This application demonstrates how to:
- Create custom annotations for AOP pointcuts
- Implement `@Around` advice to intercept method execution
- Measure and log method execution time
- Use Spring AOP with AspectJ annotations

## Features

- **Custom Annotation**: `@LogMethodExecutionTime` - A marker annotation to identify methods for AOP interception
- **Around Advice**: Intercepts method execution before and after, allowing measurement of execution time
- **Automatic Logging**: Automatically logs method execution time for any method annotated with `@LogMethodExecutionTime`

## Prerequisites

- **Java**: 21 or higher
- **Maven**: 3.6+ 
- **Spring Boot**: 3.5.7

## Project Structure

```
spring-aop-around-advice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/rslakra/springbootsamples/aoparoundadvice/
│   │   │       ├── annotation/
│   │   │       │   └── LogMethodExecutionTime.java    # Custom annotation
│   │   │       ├── aop/
│   │   │       │   └── LogMethodExecutionAspect.java  # AOP Around Advice
│   │   │       ├── service/
│   │   │       │   └── LogMethodExecutionService.java # Service with annotated method
│   │   │       └── SpringAOPAroundAdviceApplication.java
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
│       └── java/
│           └── com/rslakra/springbootsamples/aoparoundadvice/
│               ├── LogMethodExecutionServiceTest.java
│               └── SpringAspectsApplicationTest.java
├── pom.xml
├── buildMaven.sh
└── runMaven.sh
```

## Key Components

### 1. Custom Annotation
`@LogMethodExecutionTime` - A runtime annotation that can be applied to methods to enable execution time logging.

### 2. AOP Aspect
`LogMethodExecutionAspect` - Contains the `@Around` advice that:
- Intercepts methods annotated with `@LogMethodExecutionTime`
- Records the start time
- Executes the original method
- Calculates and logs the execution time

### 3. Service
`LogMethodExecutionService` - A sample service with a method annotated with `@LogMethodExecutionTime` to demonstrate the AOP functionality.

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
java -jar target/spring-aop-around-advice-0.0.1.jar
```

### Option 4: From IDE
Run the `main` method in `SpringAOPAroundAdviceApplication.java`

## Expected Output

When you run the application, you should see output similar to:

```
========================================
Spring AOP Around Advice Demo
========================================
22:07:38.006 [main] DEBUG c.r.s.a.aop.LogMethodExecutionAspect - +logMethodExecutionTime()
22:07:38.007 [main] DEBUG c.r.s.a.s.LogMethodExecutionService - logExecutionTime() - Sun Dec 07 22:07:38 PST 2025
22:07:39.018 [main] INFO  c.r.s.a.aop.LogMethodExecutionAspect - [com.rslakra.springbootsamples.aoparoundadvice.service.LogMethodExecutionService.logExecutionTime()] method took:1011ms
22:07:39.018 [main] DEBUG c.r.s.a.aop.LogMethodExecutionAspect - -logMethodExecutionTime()
Demo completed successfully!
```

## How It Works

1. The `@LogMethodExecutionTime` annotation is applied to a method
2. Spring AOP detects the annotation and applies the `@Around` advice
3. Before method execution: The aspect records the start time
4. During execution: The original method is called via `joinPoint.proceed()`
5. After execution: The aspect calculates the elapsed time and logs it

## Usage Example

To use the AOP functionality in your own methods, simply annotate them:

```java
@Component
public class MyService {
    
    @LogMethodExecutionTime
    public void myMethod() {
        // Your method logic here
    }
}
```

The execution time will be automatically logged when `myMethod()` is called.

## Testing

Run the unit tests:
```bash
mvn test
```

The project includes:
- `SpringAspectsApplicationTest` - Tests Spring context loading
- `LogMethodExecutionServiceTest` - Tests the service with AOP interception

## Dependencies

- **Spring Boot Starter AOP**: Provides AOP support
- **Spring Boot Starter Test**: Provides testing framework
- **SLF4J & Logback**: Logging framework (included with Spring Boot)

## References

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/)
* [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
* [AspectJ Documentation](https://www.eclipse.org/aspectj/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.5.7/maven-plugin/reference/html/#build-image)

## Authors

- Rohtash Lakra
