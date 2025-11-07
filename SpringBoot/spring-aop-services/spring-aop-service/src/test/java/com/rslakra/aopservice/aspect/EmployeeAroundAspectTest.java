package com.rslakra.aopservice.aspect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for EmployeeAroundAspect - @Around advice on getName()
 */
public class EmployeeAroundAspectTest extends BaseAspectTest {

    @Test
    public void testAroundAdvice() {
        System.out.println("\n=== Testing EmployeeAroundAspect.employeeAroundAdvice() - @Around ===");
        
        employee.setName("AroundTest");
        
        // This should trigger @Around("execution(* com.rslakra.aopservice.model.Employee.getName())")
        // The around advice wraps the method execution
        String name = employee.getName();
        
        assertEquals("AroundTest", name);
        System.out.println("Returned name: " + name);
        System.out.println("✓ employeeAroundAdvice() should have been called:");
        System.out.println("  - BEFORE getName() execution");
        System.out.println("  - Method execution");
        System.out.println("  - AFTER getName() execution with return value\n");
    }

    @Test
    public void testAroundAdviceWithNull() {
        System.out.println("\n=== Testing Around advice with null return ===");
        
        // Reset employee name to null to test null return handling
        // (Employee bean is a singleton, so we need to reset it)
        employee.setName(null);
        
        // Employee name should be null now
        String name = employee.getName();
        
        assertNull(name);
        System.out.println("Returned name: null");
        System.out.println("✓ Around advice should handle null return values\n");
    }

    @Test
    public void testAroundAdviceMultipleCalls() {
        System.out.println("\n=== Testing Around advice with multiple calls ===");
        
        employee.setName("First");
        String name1 = employee.getName();
        assertEquals("First", name1);
        
        employee.setName("Second");
        String name2 = employee.getName();
        assertEquals("Second", name2);
        
        System.out.println("✓ Around advice should be called for each getName() invocation\n");
    }
}

