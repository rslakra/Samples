package com.rslakra.springbootsamples.jwtauthentication.dto;

import org.hibernate.annotations.NaturalId;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Rohtash Lakra
 * @created 11/29/21 9:44 PM
 */
public class IdentityDTO extends AbstractNamedDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String userName;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

}
