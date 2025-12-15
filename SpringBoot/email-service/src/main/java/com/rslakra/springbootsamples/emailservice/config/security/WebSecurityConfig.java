package com.rslakra.springbootsamples.emailservice.config.security;

import com.rslakra.springbootsamples.emailservice.Constants;
import com.rslakra.springbootsamples.emailservice.config.SCPHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * @author Rohtash Lakra
 * @created 8/12/21 3:07 PM
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint entryPoint;

    private final JwtTokenFilter tokenFilter;

    private final SCPHelper scpHelper;

    @Qualifier("userDetailsServiceImpl")
    private final UserDetailsService userDetailsServiceImpl;

    /**
     * Public URL patterns that don't require Spring Security authentication.
     * Organized by category for better maintainability.
     * 
     * Note: Some endpoints like /dashboard and /change-password are public at Spring Security level
     * but require session-based authentication checked by controllers.
     */
    private static final String[] PUBLIC_MATCHERS = {
        // Static resources - CSS, JS, images, webjars
        "/webjars/**", "/css/**", "/js/**", "/images/**",
        
        // Public pages - home, about, contact, error pages
        "/", "/about/**", "/contact/**", "/error/**",
        
        // Development tools - H2 console, Swagger
        "/h2/**", "/h2/**", "/console/**", "/swagger-ui.html/**",
        
        // Authentication endpoints - login, signup, password reset
        // Controllers handle session validation for these
        "/login", "/signup", "/signup/**", "/authenticate",
        "/forgotPassword", "/forgotPassword/**", "/reset-password/**",
        
        // Session-protected endpoints (public at Spring Security level, 
        // but controllers check session and redirect to login if not authenticated)
        "/dashboard", "/change-password"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * @return PasswordEncoder bean (alias for BCryptPasswordEncoder)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/h2/**") // Disable CSRF for H2 console
            )
            // HTTPS requirement disabled for development
            // Uncomment below for production HTTPS requirement
            // .requiresChannel(channel -> channel
            //     .anyRequest().requiresSecure()
            // )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Changed from STATELESS to allow H2 console sessions
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .requestMatchers("/swagger-ui.html/**").permitAll()
                .requestMatchers("/h2/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/403");
                })
            )
            // Form login disabled - using custom /authenticate controller instead
            // .formLogin(form -> form
            //     .loginPage(Constants.URL_LOGIN)
            //     .defaultSuccessUrl(Constants.HOME_PAGE_URL)
            //     .failureUrl(Constants.URL_LOGIN + "?error")
            //     .permitAll()
            // )
            .logout(logout -> logout
                .logoutUrl(Constants.LOGOUT_URL)
                .logoutRequestMatcher(new AntPathRequestMatcher(Constants.LOGOUT_URL))
                .addLogoutHandler(
                    new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL)))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl(Constants.URL_LOGIN + "?logout")
                .permitAll()
            )
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin()) // Allow frames for H2 console
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self' 'unsafe-inline' 'unsafe-eval'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline'"))
                // HSTS disabled for development (HTTP)
                // Uncomment below for production HTTPS
                // .httpStrictTransportSecurity(hsts -> hsts...)
            );
        
        return httpSecurity.build();
    }
}
