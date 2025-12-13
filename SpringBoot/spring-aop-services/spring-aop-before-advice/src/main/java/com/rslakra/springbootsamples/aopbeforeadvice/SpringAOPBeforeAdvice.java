package com.rslakra.springbootsamples.aopbeforeadvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringAOPBeforeAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAOPBeforeAdvice.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        LOGGER.debug("+main({})", (Object) args);
        SpringApplication.run(SpringAOPBeforeAdvice.class, args);
        LOGGER.debug("-main()");
    }

}
