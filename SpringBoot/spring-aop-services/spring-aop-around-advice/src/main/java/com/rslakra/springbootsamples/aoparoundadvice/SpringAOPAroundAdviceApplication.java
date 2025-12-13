package com.rslakra.springbootsamples.aoparoundadvice;

import com.rslakra.springbootsamples.aoparoundadvice.service.LogMethodExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Rohtash Lakra
 * @created 1/20/22 5:14 PM
 */
@SpringBootApplication
public class SpringAOPAroundAdviceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAOPAroundAdviceApplication.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringAOPAroundAdviceApplication.class, args);
    }

    /**
     * CommandLineRunner to demonstrate AOP around advice functionality.
     *
     * @param logService
     * @return
     */
    @Bean
    public CommandLineRunner run(LogMethodExecutionService logService) {
        return args -> {
            LOGGER.info("========================================");
            LOGGER.info("Spring AOP Around Advice Demo");
            LOGGER.info("========================================");
            try {
                logService.logExecutionTime();
                LOGGER.info("Demo completed successfully!");
            } catch (InterruptedException e) {
                LOGGER.error("Demo interrupted", e);
                Thread.currentThread().interrupt();
            }
        };
    }
}
