package com.rslakra.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * OAuth2 Resource Server Configuration for Spring Boot 3.x.
 * 
 * Note: The old Spring Security OAuth2 (spring-security-oauth2) is deprecated.
 * Spring Boot 3.x uses spring-boot-starter-oauth2-resource-server.
 * 
 * For a full Authorization Server, use Spring Authorization Server:
 * https://spring.io/projects/spring-authorization-server
 */
@Configuration
public class AuthorizationServerConfig {

    /**
     * Configures JWT authentication converter to extract authorities from JWT tokens.
     * This is used when validating JWT tokens from an external authorization server.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
