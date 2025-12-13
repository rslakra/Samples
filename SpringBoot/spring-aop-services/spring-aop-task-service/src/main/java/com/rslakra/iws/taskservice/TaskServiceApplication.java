package com.rslakra.iws.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Rohtash Lakra
 * @created 5/25/22 4:43 PM
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories
public class TaskServiceApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }

}
