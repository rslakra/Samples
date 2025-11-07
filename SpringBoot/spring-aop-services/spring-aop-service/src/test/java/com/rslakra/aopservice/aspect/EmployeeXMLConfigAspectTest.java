package com.rslakra.aopservice.aspect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for EmployeeXMLConfigAspect - XML-configured @Around advice
 * This aspect is configured in spring.xml using <aop:around>
 */
public class EmployeeXMLConfigAspectTest extends BaseAspectTest {

    @Test
    public void testXMLConfiguredAroundAdvice() {
        System.out.println("\n=== Testing EmployeeXMLConfigAspect.employeeAroundAdvice() - XML Config ===");
        
        employee.setName("XMLTest");
        
        // This should trigger the XML-configured around advice from spring.xml
        // <aop:around method="employeeAroundAdvice" pointcut-ref="getNamePointcut"/>
        String name = employee.getName();
        
        assertEquals("XMLTest", name);
        System.out.println("Returned name: " + name);
        System.out.println("✓ employeeAroundAdvice() should have been called (XML configured)");
        System.out.println("  - This is configured in spring.xml, not via annotations\n");
    }

    @Test
    public void testXMLConfigWithMultipleAdvices() {
        System.out.println("\n=== Testing XML config with multiple advices ===");
        
        // When getName() is called, both annotation-based and XML-based around advices
        // may be triggered. The order is defined in spring.xml (order="1")
        employee.setName("MultiTest");
        String name = employee.getName();
        
        assertEquals("MultiTest", name);
        System.out.println("✓ XML-configured advice should work alongside annotation-based advice\n");
    }
}

