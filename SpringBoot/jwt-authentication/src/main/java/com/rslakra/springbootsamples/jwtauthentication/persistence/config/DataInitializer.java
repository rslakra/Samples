package com.rslakra.springbootsamples.jwtauthentication.persistence.config;

import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleDO;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleType;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Data initializer to create default roles on application startup
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    /**
     * Initialize default roles if they don't exist
     */
    private void initializeRoles() {
        LOGGER.info("Initializing default roles...");

        Arrays.stream(RoleType.values()).forEach(roleType -> {
            if (!roleRepository.findByName(roleType).isPresent()) {
                RoleDO role = new RoleDO();
                role.setName(roleType);
                roleRepository.save(role);
                LOGGER.info("Created role: {}", roleType);
            } else {
                LOGGER.debug("Role already exists: {}", roleType);
            }
        });

        LOGGER.info("Role initialization completed.");
    }
}

