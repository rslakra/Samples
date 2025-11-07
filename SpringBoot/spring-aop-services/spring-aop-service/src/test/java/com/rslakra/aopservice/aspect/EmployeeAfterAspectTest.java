package com.rslakra.aopservice.aspect;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for EmployeeAfterAspect - @After, @AfterReturning, @AfterThrowing advice
 */
public class EmployeeAfterAspectTest extends BaseAspectTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeAfterAspectTest.class);

    @Test
    public void testAfterAdvice() {
        LOGGER.debug("+testAfterAdvice()");
        System.out.println("\n=== Testing EmployeeAfterAspect.logStringArguments() - @After ===");

        // This should trigger @After("args(name)") after setName() completes
        employee.setName("AfterTest");

        assertEquals("AfterTest", employee.getName());
        System.out.println("✓ logStringArguments() @After should have been called\n");
        LOGGER.debug("-testAfterAdvice()");
    }

    @Test
    public void testAfterReturningAdvice() {
        System.out.println("\n=== Testing EmployeeAfterAspect.getNameReturningAdvice() - @AfterReturning ===");

        employee.setName("ReturnTest");

        // This should trigger @AfterReturning(pointcut = "execution(* getName())", returning = "returnString")
        String name = employee.getName();

        assertEquals("ReturnTest", name);
        System.out.println("Returned name: " + name);
        System.out.println("✓ getNameReturningAdvice() should have been called with returned value\n");
    }

    @Test
    public void testAfterThrowingAdvice() {
        System.out.println("\n=== Testing EmployeeAfterAspect.logExceptions() - @AfterThrowing ===");

        // This should trigger @AfterThrowing("within(com.rslakra.aopservice.model.Employee)")
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employee.throwException();
        });

        assertEquals("Runtime Exception", exception.getMessage());
        System.out.println("Exception thrown: " + exception.getMessage());
        System.out.println("✓ logExceptions() @AfterThrowing should have been called\n");
    }

    @Test
    public void testAllAfterAdvices() {
        System.out.println("\n=== Testing all After advices together ===");

        // Test @After
        employee.setName("AllTest");
        System.out.println("1. setName() called - should trigger @After");

        // Test @AfterReturning
        String name = employee.getName();
        System.out.println("2. getName() called - should trigger @AfterReturning");
        assertEquals("AllTest", name);

        // Test @AfterThrowing
        assertThrows(RuntimeException.class, () -> {
            employee.throwException();
        });
        System.out.println("3. throwException() called - should trigger @AfterThrowing");

        System.out.println("✓ All after advices should have been executed\n");
    }
}

