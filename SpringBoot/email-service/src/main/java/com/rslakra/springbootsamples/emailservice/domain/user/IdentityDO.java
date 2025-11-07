package com.rslakra.springbootsamples.emailservice.domain.user;

import com.rslakra.springbootsamples.emailservice.service.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Rohtash Lakra
 * @created 8/12/21 3:23 PM
 */
@Entity
@Table(name = "identities")
@Getter
@Setter
@NoArgsConstructor
public class IdentityDO {

    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "updated_at")
    private Date updatedAt;

}
