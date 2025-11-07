package com.rslakra.aopservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Aspect
public class EmployeeAnnotationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeAnnotationAspect.class);

    @Before("@annotation(com.rslakra.aopservice.aspect.Loggable)")
    public void logAdvice(JoinPoint joinPoint) {
        LOGGER.debug("+logAdvice({})", joinPoint);
        LOGGER.info("Calling [{}()] with arguments={}", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        
        HttpServletRequest servletRequest = null;
        int servletRequestHash = 0;
        
        // Try to get current request from the context (may not be available in test context)
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                servletRequest = attributes.getRequest();
                if (servletRequest != null) {
                    servletRequestHash = Objects.hashCode(servletRequest);
                    LOGGER.info("threadName: {}, servletRequestHash: {}, servletRequest's Hash:{}",
                            Thread.currentThread().getName(), servletRequestHash, servletRequest.hashCode());
                    
                    // Reduced wait time for testing - use 10ms instead of 2000ms
                    // In production, you might want to use a configurable value
                    try {
                        Long waitTime = Long.valueOf(10L);
                        LOGGER.debug("Waiting for {} millis ...", waitTime);
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ex) {
                        LOGGER.error("Ignore me");
                    }

                    if (servletRequestHash != servletRequest.hashCode()) {
                        LOGGER.error("threadName: {}, servletRequestHash: {}, servletRequest's Hash:{}",
                                Thread.currentThread().getName(), servletRequestHash, servletRequest.hashCode());
                        throw new RuntimeException("Request hash should not change!");
                    }
                } else {
                    LOGGER.debug("HttpServletRequest is null - not in web context");
                }
            } else {
                LOGGER.debug("No RequestAttributes found - not in web context (e.g., unit test)");
            }
        } catch (IllegalStateException e) {
            // No request context available (e.g., in unit tests)
            LOGGER.debug("No request context available: {}", e.getMessage());
        }

        LOGGER.debug("-logAdvice()");
    }
}
