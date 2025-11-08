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
public class DashboardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    /**
     * Display the user dashboard
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        LOGGER.info("Dashboard page requested");
        
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = false;
        boolean isAdmin = false;
        String username = null;
        
        if (session != null) {
            Object isValidUser = session.getAttribute("isValidUser");
            if (isValidUser != null && (Boolean) isValidUser) {
                isLoggedIn = true;
                isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
                username = (String) session.getAttribute("username");
                LOGGER.info("User is logged in: {}, isAdmin: {}, username: {}", isLoggedIn, isAdmin, username);
            } else {
                LOGGER.warn("Session exists but user is not authenticated");
            }
        } else {
            LOGGER.warn("No session found");
        }
        
        // Redirect to login if not authenticated
        if (!isLoggedIn) {
            LOGGER.info("User not authenticated, redirecting to login");
            return "redirect:/login";
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        
        LOGGER.info("Rendering dashboard template");
        return "dashboard";
    }
}

