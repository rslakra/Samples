package com.rslakra.springbootsamples.jwtauthentication.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleDO extends AbstractDO {

    @NaturalId
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 64, unique = true, nullable = false)
    private RoleType name;

}
