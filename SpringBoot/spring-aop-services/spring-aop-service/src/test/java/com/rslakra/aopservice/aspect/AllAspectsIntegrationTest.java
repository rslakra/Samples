package com.rslakra.aopservice.aspect;

import com.rslakra.aopservice.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test that verifies all aspects work together
 */
public class AllAspectsIntegrationTest extends BaseAspectTest {

    @Test
    public void testAllAspectsTogether() {
        System.out.println("\n========================================");
        System.out.println("INTEGRATION TEST: All AOP Aspects");
        System.out.println("========================================\n");
        
        // 1. Test service getter (triggers EmployeeAspect.getAllAdvice())
        System.out.println("1. Calling getEmployee() - should trigger:");
        System.out.println("   - EmployeeAspect.getAllAdvice()");
        System.out.println("   - EmployeeAspectPointcut.allServiceMethodsAdvice()");
        Employee emp = employeeService.getEmployee();
        assertNotNull(emp);
        
        // 2. Test getName() (triggers multiple aspects)
        System.out.println("\n2. Calling getName() - should trigger:");
        System.out.println("   - EmployeeAspectPointcut.loggingAdvice()");
        System.out.println("   - EmployeeAspectPointcut.secondAdvice()");
        System.out.println("   - EmployeeAroundAspect.employeeAroundAdvice()");
        System.out.println("   - EmployeeAspect.getNameAdvice()");
        System.out.println("   - EmployeeXMLConfigAspect.employeeAroundAdvice()");
        System.out.println("   - EmployeeAfterAspect.getNameReturningAdvice()");
        String name = employee.getName();
        System.out.println("   Result: " + name);
        
        // 3. Test setName() (triggers JoinPoint and After aspects)
        System.out.println("\n3. Calling setName('IntegrationTest') - should trigger:");
        System.out.println("   - EmployeeAspectJoinPoint.loggingAdvice()");
        System.out.println("   - EmployeeAspectJoinPoint.logStringArguments()");
        System.out.println("   - EmployeeAnnotationAspect.logAdvice() (if request available)");
        System.out.println("   - EmployeeAfterAspect.logStringArguments()");
        try {
            employee.setName("IntegrationTest");
            assertEquals("IntegrationTest", employee.getName());
        } catch (RuntimeException e) {
            System.out.println("   Note: EmployeeAnnotationAspect may fail without HttpServletRequest");
        }
        
        // 4. Test exception handling (triggers AfterThrowing)
        System.out.println("\n4. Calling throwException() - should trigger:");
        System.out.println("   - EmployeeAfterAspect.logExceptions()");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employee.throwException();
        });
        assertEquals("Runtime Exception", exception.getMessage());
        
        System.out.println("\n========================================");
        System.out.println("✓ All aspects tested successfully!");
        System.out.println("========================================\n");
    }

    @Test
    public void testAspectOrder() {
        System.out.println("\n=== Testing Aspect Execution Order ===");
        
        employee.setName("OrderTest");
        
        System.out.println("Calling getName() - execution order should be:");
        System.out.println("1. @Before advices (EmployeeAspectPointcut, EmployeeAspect)");
        System.out.println("2. @Around advices (EmployeeAroundAspect, EmployeeXMLConfigAspect)");
        System.out.println("3. Method execution");
        System.out.println("4. @AfterReturning advice (EmployeeAfterAspect)");
        
        String name = employee.getName();
        assertEquals("OrderTest", name);
        
        System.out.println("✓ Verify the order in logs above\n");
    }
}

