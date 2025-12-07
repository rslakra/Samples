package com.rslakra.springbootsamples.jwtauthentication.security.jwt;

import com.rslakra.springbootsamples.jwtauthentication.security.services.UserPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 *
 */
@Component
public class JwtProvider {

    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpiration}")
    private int jwtExpiration;

    /**
     * Get the secret key for signing JWT tokens
     *
     * @return SecretKey
     */
    private SecretKey getSigningKey() {
        // Ensure the secret is at least 256 bits (32 bytes) for HS512
        byte[] keyBytes = jwtSecret.getBytes();
        if (keyBytes.length < 32) {
            // Pad or extend the key to meet minimum requirement
            byte[] paddedKey = new byte[32];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
            return Keys.hmacShaKeyFor(paddedKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * @param jwtExpirationInMinutes
     * @return
     */
    public static Long getExpiryTime(int jwtExpirationInMinutes) {
        return (new Date().getTime() + jwtExpirationInMinutes * 10000);
    }

    /**
     * @param authentication
     * @return
     */
    public String createJWTToken(final Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
            .subject(userPrincipal.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(getExpiryTime(jwtExpiration)))
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(final String authToken) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature -> Message: {} ", ex);
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token -> Message: {}", ex);
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token -> Message: {}", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token -> Message: {}", ex);
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty -> Message: {}", ex);
        }

        return false;
    }

    /**
     * @param jwtToken
     * @return
     */
    public String getPrincipleFromJwtToken(final String jwtToken) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(jwtToken)
            .getPayload()
            .getSubject();
    }
}
