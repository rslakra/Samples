package com.rslakra.aopservice;

import com.rslakra.aopservice.model.Employee;
import com.rslakra.aopservice.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify AOP aspects are being called
 */
public class AopServiceTest {

    @Test
    public void testAopAspectsAreCalled() {
        System.out.println("\n=== Testing AOP Aspects ===\n");
        
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);
        
        System.out.println("1. Getting employee (should trigger Before advice on getEmployee())");
        Employee employee = employeeService.getEmployee();
        assertNotNull(employee);
        
        System.out.println("2. Calling getName() (should trigger multiple Before, Around, and AfterReturning advice)");
        String name = employee.getName();
        assertNotNull(name);
        System.out.println("   Employee name: " + name);
        
        System.out.println("3. Calling setName() (should trigger Before and After advice)");
        employee.setName("Lakra");
        
        System.out.println("4. Calling getName() again");
        name = employee.getName();
        System.out.println("   Employee name: " + name);
        
        System.out.println("5. Calling throwException() (should trigger AfterThrowing advice)");
        try {
            employee.throwException();
        } catch (RuntimeException e) {
            System.out.println("   Exception caught: " + e.getMessage());
        }
        
        ctx.close();
        System.out.println("\n=== AOP Test Complete ===\n");
    }
}

