package com.rslakra.springbootsamples.jwtauthentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.IdentityDO;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleDO;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleType;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.IdentityRepository;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.RoleRepository;
import com.rslakra.springbootsamples.jwtauthentication.security.jwt.JwtProvider;
import com.rslakra.springbootsamples.jwtauthentication.security.services.UserPrinciple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String BASE_URL = "/rest/v1";

    @BeforeEach
    void setUp() {
        identityRepository.deleteAll();
        roleRepository.deleteAll();
        initializeRoles();
    }

    private void initializeRoles() {
        for (RoleType roleType : RoleType.values()) {
            if (!roleRepository.findByName(roleType).isPresent()) {
                RoleDO role = new RoleDO();
                role.setName(roleType);
                roleRepository.save(role);
            }
        }
    }

    private String createJwtToken(String username, RoleType roleType) {
        Set<RoleDO> roles = new HashSet<>();
        RoleDO role = roleRepository.findByName(roleType)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(role);

        IdentityDO identity = new IdentityDO();
        identity.setName(username);
        identity.setUserName(username);
        identity.setEmail(username + "@example.com");
        identity.setPassword("password123");
        identity.setRoles(roles);
        
        // Save identity to get an ID
        identity = identityRepository.save(identity);

        UserPrinciple userPrinciple = UserPrinciple.build(identity);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userPrinciple, null, userPrinciple.getAuthorities());

        return jwtProvider.createJWTToken(authentication);
    }

    @Test
    void testUserAccess_WithUserRole() throws Exception {
        String token = createJwtToken("testuser", RoleType.USER);

        mockMvc.perform(get(BASE_URL + "/user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(">>> IdentityDO Contents!"));
    }

    @Test
    void testUserAccess_WithAdminRole() throws Exception {
        String token = createJwtToken("adminuser", RoleType.ADMIN);

        mockMvc.perform(get(BASE_URL + "/user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(">>> IdentityDO Contents!"));
    }

    @Test
    void testUserAccess_WithoutToken() throws Exception {
        mockMvc.perform(get(BASE_URL + "/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testManagerAccess_WithManagerRole() throws Exception {
        String token = createJwtToken("manageruser", RoleType.MANAGER);

        mockMvc.perform(get(BASE_URL + "/manager")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(">>> Board Management Project"));
    }

    @Test
    void testManagerAccess_WithUserRole_ShouldFail() throws Exception {
        String token = createJwtToken("testuser", RoleType.USER);

        mockMvc.perform(get(BASE_URL + "/manager")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdminAccess_WithAdminRole() throws Exception {
        String token = createJwtToken("adminuser", RoleType.ADMIN);

        mockMvc.perform(get(BASE_URL + "/admin")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(">>> Admin Contents"));
    }

    @Test
    void testAdminAccess_WithUserRole_ShouldFail() throws Exception {
        String token = createJwtToken("testuser", RoleType.USER);

        mockMvc.perform(get(BASE_URL + "/admin")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGuestAccess_WithGuestRole() throws Exception {
        String token = createJwtToken("guestuser", RoleType.GUEST);

        mockMvc.perform(get(BASE_URL + "/guest")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(">>> Guest Contents"));
    }
}

