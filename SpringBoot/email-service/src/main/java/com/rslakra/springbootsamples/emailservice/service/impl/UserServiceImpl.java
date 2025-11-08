package com.rslakra.springbootsamples.emailservice.service.impl;

import com.rslakra.springbootsamples.emailservice.domain.user.IdentityDO;
import com.rslakra.springbootsamples.emailservice.dto.UserRequest;
import com.rslakra.springbootsamples.emailservice.repository.IdentityRepository;
import com.rslakra.springbootsamples.emailservice.service.UserService;
import com.rslakra.springbootsamples.emailservice.service.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Rohtash Lakra
 * @created 1/6/22 4:21 PM
 */
@Service
public class UserServiceImpl implements UserService {

    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * @param userRequest
     * @return
     */
    public String saveUser(UserRequest userRequest) {
        IdentityDO identity = new IdentityDO();
        identity.setUserName(userRequest.getUserName());
        identity.setFirstName(userRequest.getFirstName());
        identity.setLastName(userRequest.getLastName());
        identity.setEmail(userRequest.getEmail());
        identity.setPhoneNumber(userRequest.getPhoneNumber());
        // Use ID from request if provided, otherwise use phoneNumber, otherwise use email
        String id = userRequest.getId();
        if (id == null || id.isEmpty()) {
            id = userRequest.getPhoneNumber();
        }
        if (id == null || id.isEmpty()) {
            id = userRequest.getEmail();
        }
        identity.setId(id);
        identity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        identity.setStatus(UserStatus.ACTIVE);
        identity.setCreatedAt(new Date());
        identity.setUpdatedAt(new Date());
        identityRepository.save(identity);
        LOGGER.info("Saving user with userId:{}", identity.getId());
        return identity.getId();
    }

    public void updatePassword(String userId, String password) {
        identityRepository.updatePassword(userId, password);
    }

}
