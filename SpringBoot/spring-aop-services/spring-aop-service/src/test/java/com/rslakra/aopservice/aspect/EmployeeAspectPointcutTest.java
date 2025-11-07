package com.rslakra.aopservice.aspect;

import com.rslakra.aopservice.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for EmployeeAspectPointcut - Pointcut-based @Before advice
 */
public class EmployeeAspectPointcutTest extends BaseAspectTest {

    @Test
    public void testGetNamePointcut() {
        System.out.println("\n=== Testing EmployeeAspectPointcut.getNamePointcut() ===");
        
        // This should trigger both loggingAdvice() and secondAdvice() 
        // which use @Before("getNamePointcut()")
        String name = employee.getName();
        
        assertNotNull(name);
        System.out.println("Employee name: " + name);
        System.out.println("✓ loggingAdvice() should have been called");
        System.out.println("✓ secondAdvice() should have been called");
        System.out.println("Both use the getNamePointcut() pointcut\n");
    }

    @Test
    public void testAllMethodsPointcut() {
        System.out.println("\n=== Testing EmployeeAspectPointcut.allMethodsPointcut() ===");
        
        // This should trigger allServiceMethodsAdvice() which uses 
        // @Before("allMethodsPointcut()") where pointcut is "within(com.rslakra.aopservice.service.*)"
        Employee emp = employeeService.getEmployee();
        employeeService.setEmployee(emp);
        
        assertNotNull(emp);
        System.out.println("✓ allServiceMethodsAdvice() should have been called for service methods\n");
    }
}

