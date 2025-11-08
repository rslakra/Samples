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
        
        if (session != null) {
            Object isValidUser = session.getAttribute("isValidUser");
            if (isValidUser != null && (Boolean) isValidUser) {
                isLoggedIn = true;
                isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
                username = (String) session.getAttribute("username");
            }
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        
        // Check for success messages
        if (request.getParameter("changeSuccess") != null) {
            model.addAttribute("successMessage", "Password changed successfully!");
        }
        
        return "index";
    }
}

