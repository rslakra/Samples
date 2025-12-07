package com.rslakra.springbootsamples.jwtauthentication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Rohtash Lakra
 * @created 11/29/21 9:24 PM
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractNamedDTO extends AbstractDTO {

    @NotBlank
    @Size(min = 3, max = 64)
    private String name;

}
