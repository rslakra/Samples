package com.rslakra.springbootsamples.emailservice.controller.web;

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
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);

    /**
     * Display the forgot password page
     *
     * @param model
     * @return
     */
    @GetMapping
    public String showForgotPasswordPage(Model model) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Forgot password page requested");
        }
        return "forgot-password";
    }
}

