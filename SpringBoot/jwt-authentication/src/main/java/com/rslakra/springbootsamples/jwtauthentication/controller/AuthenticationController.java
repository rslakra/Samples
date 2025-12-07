package com.rslakra.springbootsamples.jwtauthentication.controller;

import com.rslakra.springbootsamples.jwtauthentication.payload.request.LoginRequest;
import com.rslakra.springbootsamples.jwtauthentication.payload.request.SignUpRequest;
import com.rslakra.springbootsamples.jwtauthentication.payload.response.JwtResponse;
import com.rslakra.springbootsamples.jwtauthentication.payload.response.MessageResponse;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.IdentityDO;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleDO;
import com.rslakra.springbootsamples.jwtauthentication.persistence.model.RoleType;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.IdentityRepository;
import com.rslakra.springbootsamples.jwtauthentication.persistence.repository.RoleRepository;
import com.rslakra.springbootsamples.jwtauthentication.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${apiPrefix}/auth")
//@Tag(name = "Authentication Service")
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final IdentityRepository identityRepository;

    /**
     * @param passwordEncoder
     * @param roleRepository
     * @param jwtProvider
     * @param authenticationManager
     * @param identityRepository
     */
    @Autowired
    public AuthenticationController(PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                                    JwtProvider jwtProvider, AuthenticationManager authenticationManager,
                                    IdentityRepository identityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.identityRepository = identityRepository;
    }

    /**
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication
            authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.createJWTToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /**
     * @param signUpRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (identityRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("Fail -> Username is already taken!"));
        }

        if (identityRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("Fail -> Email is already in use!"));
        }

        // Creating identity's account
        IdentityDO
            identity =
            new IdentityDO(signUpRequest.getName(), signUpRequest.getUserName(),
                           passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail());

        final Set<String> strRoles = signUpRequest.getRoles();
        Set<RoleDO> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    RoleDO adminRole = roleRepository.findByName(RoleType.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Role not found."));
                    roles.add(adminRole);

                    break;
                case "manager":
                    RoleDO managerRole = roleRepository.findByName(RoleType.MANAGER)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Role not found."));
                    roles.add(managerRole);

                    break;
                case "user":
                    RoleDO userRole = roleRepository.findByName(RoleType.USER)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Role not found."));
                    roles.add(userRole);

                    break;
                default:
                    RoleDO guestRole = roleRepository.findByName(RoleType.GUEST)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Role not found."));
                    roles.add(guestRole);
            }
        });

        identity.setRoles(roles);
        identity = identityRepository.save(identity);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
