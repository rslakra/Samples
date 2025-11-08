package com.rslakra.springbootsamples.emailservice.utils;

import org.apache.commons.text.StringEscapeUtils;

/**
 * Security utility class for input validation and output encoding.
 * Replaces ESAPI functionality with Apache Commons Text and standard Java validation.
 *
 * @author Rohtash Lakra
 * @created 11/7/24
 */
public class SecurityUtils {

    /**
     * Encodes a string for safe HTML output (prevents XSS attacks).
     * Replaces ESAPI.encoder().encodeForHTML()
     *
     * @param input the string to encode
     * @return HTML-encoded string, or empty string if input is null
     */
    public static String encodeForHTML(String input) {
        if (input == null) {
            return "";
        }
        return StringEscapeUtils.escapeHtml4(input);
    }

    /**
     * Validates username format.
     * Username must be 3-50 characters, alphanumeric with underscores only.
     *
     * @param username the username to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        // Username: 3-50 characters, alphanumeric and underscores only
        return username.matches("^[a-zA-Z0-9_]{3,50}$");
    }

    /**
     * Validates email format.
     *
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Email validation regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex) && email.length() <= 100;
    }

    /**
     * Validates input to prevent XSS attacks.
     * Checks for common XSS patterns.
     *
     * @param input the input to validate
     * @param maxLength maximum allowed length
     * @return true if input is safe, false if it contains suspicious patterns
     */
    public static boolean isSafeInput(String input, int maxLength) {
        if (input == null) {
            return true; // null is considered safe
        }
        if (input.length() > maxLength) {
            return false;
        }
        // Check for common XSS patterns
        String lowerInput = input.toLowerCase();
        return !lowerInput.contains("<script") 
            && !lowerInput.contains("</script>")
            && !lowerInput.contains("javascript:")
            && !lowerInput.contains("onerror=")
            && !lowerInput.contains("onload=")
            && !lowerInput.contains("<iframe");
    }

    /**
     * Validates integer value within a range.
     *
     * @param value the value to validate
     * @param minValue minimum allowed value
     * @param maxValue maximum allowed value
     * @return true if valid, false otherwise
     */
    public static boolean isValidInteger(String value, int minValue, int maxValue) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            int intValue = Integer.parseInt(value.trim());
            return intValue >= minValue && intValue <= maxValue;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

