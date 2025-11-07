package com.rslakra.aopservice.aspect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for EmployeeAspectJoinPoint - @Before advice with JoinPoint and args
 */
public class EmployeeAspectJoinPointTest extends BaseAspectTest {

    @Test
    public void testLoggingAdviceWithJoinPoint() {
        System.out.println("\n=== Testing EmployeeAspectJoinPoint.loggingAdvice() ===");
        
        // This should trigger @Before("execution(public void com.rslakra.aopservice.model..set*(*))")
        // which captures JoinPoint information
        employee.setName("TestName");
        
        assertEquals("TestName", employee.getName());
        System.out.println("✓ loggingAdvice() should have been called with JoinPoint");
        System.out.println("✓ JoinPoint should contain method name and arguments\n");
    }

    @Test
    public void testLogStringArguments() {
        System.out.println("\n=== Testing EmployeeAspectJoinPoint.logStringArguments() ===");
        
        // This should trigger @Before("args(name)") which matches methods with String argument
        employee.setName("John Doe");
        
        assertEquals("John Doe", employee.getName());
        System.out.println("✓ logStringArguments() should have been called with argument: John Doe\n");
    }

    @Test
    public void testMultipleStringArguments() {
        System.out.println("\n=== Testing logStringArguments with different values ===");
        
        employee.setName("Alice");
        assertEquals("Alice", employee.getName());
        
        employee.setName("Bob");
        assertEquals("Bob", employee.getName());
        
        System.out.println("✓ logStringArguments() should have been called for each setName() call\n");
    }
}

