package com.rslakra.springbootsamples.jwtauthentication.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "identities", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "userName"
    }),
    @UniqueConstraint(columnNames = {
        "email"
    })
})
public class IdentityDO extends AbstractNamedDO {

    private String userName;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleDO> roles = new HashSet<>();

    /**
     * @param name
     * @param userName
     * @param password
     * @param email
     */
    public IdentityDO(String name, String userName, String password, String email) {
        setName(name);
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

}
