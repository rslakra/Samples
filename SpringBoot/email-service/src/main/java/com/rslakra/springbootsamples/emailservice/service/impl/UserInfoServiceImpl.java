package com.rslakra.springbootsamples.emailservice.service.impl;

import com.rslakra.springbootsamples.emailservice.domain.user.IdentityDO;
import com.rslakra.springbootsamples.emailservice.domain.user.UserInfo;
import com.rslakra.springbootsamples.emailservice.repository.IdentityRepository;
import com.rslakra.springbootsamples.emailservice.service.UserInfoService;
import com.rslakra.springbootsamples.emailservice.service.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rohtash Lakra
 * @created 1/6/22 5:18 PM
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private IdentityRepository identityRepository;

    @Override
    public UserInfo getUserByName(String username) {
        IdentityDO identity = identityRepository.findByUserNameAndStatus(username, UserStatus.ACTIVE);
        if (identity == null) {
            return null;
        }
        
        UserInfo userInfo = new UserInfo();
        userInfo.setId(identity.getId());
        userInfo.setUserName(identity.getUserName());
        userInfo.setPassword(identity.getPassword());
        userInfo.setFirstName(identity.getFirstName());
        userInfo.setLastName(identity.getLastName());
        userInfo.setEmail(identity.getEmail());
        userInfo.setPhoneNumber(identity.getPhoneNumber());
        // Note: salt and roleId are not in IdentityDO, may need to be added or fetched separately
        return userInfo;
    }
}

