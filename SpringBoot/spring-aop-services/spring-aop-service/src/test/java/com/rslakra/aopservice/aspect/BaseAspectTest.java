package com.rslakra.aopservice.aspect;

import com.rslakra.aopservice.model.Employee;
import com.rslakra.aopservice.service.EmployeeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Base test class for all AOP aspect tests.
 * Provides common setup and teardown for Spring context and beans.
 * 
 * Uses @BeforeAll/@AfterAll to share Spring context across all test methods
 * in the same test class for better performance.
 */
public abstract class BaseAspectTest {

    protected static ClassPathXmlApplicationContext ctx;
    protected EmployeeService employeeService;
    protected Employee employee;

    @BeforeAll
    public static void setUpClass() {
        // Create Spring context once per test class
        ctx = new ClassPathXmlApplicationContext("spring.xml");
    }

    @BeforeEach
    public void setUp() {
        // Get beans from the shared context for each test method
        employeeService = ctx.getBean("employeeService", EmployeeService.class);
        employee = ctx.getBean("advice", Employee.class);
    }

    @AfterAll
    public static void tearDownClass() {
        // Close context once after all tests in the class
        if (ctx != null) {
            ctx.close();
        }
    }
}

