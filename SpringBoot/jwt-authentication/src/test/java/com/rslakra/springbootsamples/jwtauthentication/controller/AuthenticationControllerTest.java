package com.rslakra.springbootsamples.jwtauthentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleDO;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleType;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.IdentityRepository;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.RoleRepository;
import com.rslakra.springbootsamples.jwtauthentication.payload.request.LoginRequest;
import com.rslakra.springbootsamples.jwtauthentication.payload.request.SignUpRequest;
import com.rslakra.springbootsamples.jwtauthentication.payload.response.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String BASE_URL = "/api/v1/auth";

    @BeforeEach
    void setUp() {
        // Clean up before each test
        identityRepository.deleteAll();
        roleRepository.deleteAll();

        // Initialize roles
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

    @Test
    void testRegisterUser_Success() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setUserName("testuser");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signUpRequest.setRoles(roles);

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void testRegisterUser_DuplicateUsername() throws Exception {
        // First registration
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setUserName("testuser");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signUpRequest.setRoles(roles);

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        // Second registration with same username
        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Fail -> Username is already taken!"));
    }

    @Test
    void testRegisterUser_DuplicateEmail() throws Exception {
        // First registration
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setUserName("testuser");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signUpRequest.setRoles(roles);

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        // Second registration with same email
        signUpRequest.setUserName("testuser2");
        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Fail -> Email is already in use!"));
    }

    @Test
    void testLoginUser_Success() throws Exception {
        // First register a user
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setUserName("testuser");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signUpRequest.setRoles(roles);

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        // Then login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("testuser");
        loginRequest.setPassword("password123");

        MvcResult result = mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(responseContent, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());
        assertTrue(jwtResponse.getAccessToken().length() > 0);
    }

    @Test
    void testLoginUser_InvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("nonexistent");
        loginRequest.setPassword("wrongpassword");

        mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegisterUser_WithAdminRole() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Admin User");
        signUpRequest.setUserName("adminuser");
        signUpRequest.setEmail("admin@example.com");
        signUpRequest.setPassword("admin123");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        signUpRequest.setRoles(roles);

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());
    }
}

