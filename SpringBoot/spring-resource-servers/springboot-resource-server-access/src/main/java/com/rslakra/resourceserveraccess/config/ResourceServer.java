package com.rslakra.resourceserveraccess.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Resource Server Security Configuration for Spring Boot 3.x.
 * 
 * Note: The old @EnableResourceServer, ResourceServerConfigurerAdapter, and
 * WebSecurityConfigurerAdapter are deprecated in Spring Security 6.x.
 * Spring Boot 3.x uses SecurityFilterChain beans for security configuration.
 * 
 * For OAuth2 Resource Server with JWT validation, configure:
 * 1. spring.security.oauth2.resourceserver.jwt.issuer-uri in application.properties
 * 2. Or provide a JwtDecoder bean
 * 
 * For a full OAuth2 setup, see:
 * https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html
 */
@Configuration
@EnableWebSecurity
public class ResourceServer {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/user/getEmployees").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.permitAll())
            .logout(logout -> logout.permitAll())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
