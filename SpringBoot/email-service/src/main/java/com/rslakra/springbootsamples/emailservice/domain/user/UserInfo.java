package com.rslakra.springbootsamples.emailservice.domain.user;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.springbootsamples.emailservice.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Rohtash Lakra
 * @created 1/6/22 5:18 PM
 */
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    private String id;
    private String userName;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String roleId;

    public boolean isUser() {
        // Check if user is not admin (assuming non-admin users are regular users)
        return roleId != null && !roleId.equals(Constants.ADMIN_ROLE_ID);
    }

    public boolean hasPassword() {
        return !BeanUtils.isEmpty(password);
    }

    public boolean isAdmin() {
        return roleId != null && roleId.equals(Constants.ADMIN_ROLE_ID);
    }
}
