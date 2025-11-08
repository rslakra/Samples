package com.rslakra.springbootsamples.emailservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rohtash Lakra
 * @created 11/7/24
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    /**
     * Display the home page
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Home page requested");
        }
        
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = false;
        boolean isAdmin = false;
        String username = null;
        String displayName = null;
        
        if (session != null) {
            Object isValidUser = session.getAttribute("isValidUser");
            if (isValidUser != null && (Boolean) isValidUser) {
                isLoggedIn = true;
                isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
                username = (String) session.getAttribute("username");
                displayName = (String) session.getAttribute("displayName");
            }
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        model.addAttribute("displayName", displayName);
        
        // Check for success messages
        if (request.getParameter("changeSuccess") != null) {
            model.addAttribute("successMessage", "Password changed successfully!");
        }
        
        return "index";
    }

    /**
     * Display the user dashboard
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        LOGGER.info("=== Dashboard page requested ===");
        LOGGER.info("Request URI: {}", request.getRequestURI());
        
        // Try to get existing session first, but don't create a new one yet
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = false;
        boolean isAdmin = false;
        String username = null;
        String displayName = null;
        
        if (session != null) {
            LOGGER.info("Session found, ID: {}", session.getId());
            LOGGER.info("Checking session attributes...");
            
            // Log all session attributes for debugging
            java.util.Enumeration<String> attrNames = session.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String attrName = attrNames.nextElement();
                LOGGER.info("Session attribute: {} = {}", attrName, session.getAttribute(attrName));
            }
            
            Object isValidUser = session.getAttribute("isValidUser");
            LOGGER.info("isValidUser attribute: {}", isValidUser);
            
            if (isValidUser != null && (Boolean) isValidUser) {
                isLoggedIn = true;
                Object isAdminObj = session.getAttribute("isAdmin");
                isAdmin = isAdminObj != null && (Boolean) isAdminObj;
                username = (String) session.getAttribute("username");
                displayName = (String) session.getAttribute("displayName");
                LOGGER.info("User is logged in: {}, isAdmin: {}, username: {}, displayName: {}", 
                            isLoggedIn, isAdmin, username, displayName);
            } else {
                LOGGER.warn("Session exists but user is not authenticated. isValidUser: {}", isValidUser);
            }
        } else {
            LOGGER.warn("No session found - user may not be logged in or session expired");
        }
        
        // Redirect to login if not authenticated
        if (!isLoggedIn) {
            LOGGER.info("User not authenticated, redirecting to login");
            return "redirect:/login";
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        model.addAttribute("displayName", displayName);
        
        LOGGER.info("Rendering dashboard template with attributes - isLoggedIn: {}, isAdmin: {}, username: {}, displayName: {}", 
                    isLoggedIn, isAdmin, username, displayName);
        return "dashboard";
    }
}

