package com.rslakra.springbootsamples.emailservice.config.security;

import com.rslakra.springbootsamples.emailservice.service.AuthUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// WebSecurityConfigurerAdapter removed in Spring Security 6.x - this class is deprecated and disabled
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Rohtash Lakra
 * @created 8/12/21 3:28 PM
 * @deprecated This configuration has been merged into WebSecurityConfig.
 *             This class is kept for reference but disabled.
 *             To re-enable, uncomment @Configuration and @EnableWebSecurity annotations.
 */
// @Configuration - Disabled: merged into WebSecurityConfig
// @EnableWebSecurity - Disabled: merged into WebSecurityConfig
@Deprecated
public class SpringSecurityConfig {

    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Autowired
    private AuthUserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS = {
        "/webjars/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/",
        "/about/**",
        "/contact/**",
        "/error/**/*",
        "/console/**",
        "/signup",
        "/signup/user",
        "/forgotPassword**",
        "/reset-password**"
    };

    // configure method removed - this class is deprecated and disabled
    // Configuration has been merged into WebSecurityConfig

    /**
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        LOGGER.debug("authenticationProvider called for authorization");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }

    /**
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManagerBuilder configure method removed - this class is deprecated and disabled
    // AuthenticationManager and UserDetailsService beans are now in WebSecurityConfig
}
