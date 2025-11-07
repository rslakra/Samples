package com.rslakra.springbootsamples.emailservice.controller.web;

import com.rslakra.springbootsamples.emailservice.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rohtash Lakra
 * @created 11/7/24
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

    /**
     * Display the signup page
     *
     * @param model
     * @return
     */
    @GetMapping
    public String showSignupPage(Model model) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Signup page requested");
        }
        return "signup";
    }
}

