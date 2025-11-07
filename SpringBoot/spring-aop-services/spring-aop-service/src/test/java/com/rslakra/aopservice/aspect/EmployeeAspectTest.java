package com.rslakra.aopservice.aspect;

import com.rslakra.aopservice.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for EmployeeAspect - @Before advice on getName() and service getters
 */
public class EmployeeAspectTest extends BaseAspectTest {

    @Test
    public void testGetNameAdvice() {
        System.out.println("\n=== Testing EmployeeAspect.getNameAdvice() ===");
        
        // This should trigger @Before("execution(public String getName())")
        String name = employee.getName();
        
        assertNotNull(name);
        System.out.println("Employee name: " + name);
        System.out.println("✓ getNameAdvice() should have been called before getName()\n");
    }

    @Test
    public void testGetAllAdvice() {
        System.out.println("\n=== Testing EmployeeAspect.getAllAdvice() ===");
        
        // This should trigger @Before("execution(* com.rslakra.aopservice.service.*.get*())")
        // Using employeeService.getEmployee() to test the service getter advice
        Employee emp = employeeService.getEmployee();
        
        assertNotNull(emp);
        System.out.println("✓ getAllAdvice() should have been called before getEmployee()\n");
    }
}

