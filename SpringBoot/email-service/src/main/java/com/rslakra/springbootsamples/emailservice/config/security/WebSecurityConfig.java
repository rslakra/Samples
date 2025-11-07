package com.rslakra.springbootsamples.emailservice.config.security;


import com.rslakra.springbootsamples.emailservice.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint entryPoint;

    private final JwtTokenFilter tokenFilter;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SCPHelper scpHelper;

    @Qualifier("userDetailsServiceImpl")
    private final UserDetailsService userDetailsServiceImpl;

    // Public URL patterns that don't require authentication
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
        "/reset-password**",
        "/login"
    };

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth)
        throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
        throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @return UserDetailsService bean
     * @throws Exception
     */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    /**
     * @return PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return BCryptPasswordEncoder bean
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity)
        throws Exception {
        httpSecurity
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository
                                     .withHttpOnlyFalse()
            )
            .and()
            .requiresChannel()
            .anyRequest()
            .requiresSecure()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(entryPoint)
            .accessDeniedPage("/403")
            .and()
            .formLogin()
            .loginPage(Constants.URL_LOGIN)
            .defaultSuccessUrl(Constants.HOME_PAGE_URL)
            .failureUrl(Constants.URL_LOGIN + "?error")
            .permitAll()
            .and()
            .logout()
            .logoutUrl(Constants.LOGOUT_URL)
            .logoutRequestMatcher(new AntPathRequestMatcher(Constants.LOGOUT_URL))
            .addLogoutHandler(
                new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL)))
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .logoutSuccessUrl(Constants.URL_LOGIN + "?logout")
            .permitAll()
            .and()
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS)
            .permitAll()
            .antMatchers("/swagger-ui.html/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .headers()
            .contentSecurityPolicy(scpHelper.getAllowList() != null ? scpHelper.getAllowList() : "default-src 'self'")
            .and()
            .httpStrictTransportSecurity()
        ;
    }
}
