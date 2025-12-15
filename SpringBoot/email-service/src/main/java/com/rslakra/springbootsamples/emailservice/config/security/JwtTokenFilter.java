package com.rslakra.springbootsamples.emailservice.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Rohtash Lakra
 * @created 1/6/22 4:50 PM
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Skip JWT filter for H2 console
        String path = httpServletRequest.getRequestURI();
        if (path != null && (path.startsWith("/h2") || path.startsWith("/h2"))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        
        // TODO: Implement JWT token validation
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
