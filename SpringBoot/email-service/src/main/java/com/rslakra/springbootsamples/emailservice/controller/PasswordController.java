package com.rslakra.springbootsamples.emailservice.controller;

import com.rslakra.springbootsamples.emailservice.domain.user.IdentityDO;
import com.rslakra.springbootsamples.emailservice.dto.LoggedInUserRequest;
import com.rslakra.springbootsamples.emailservice.dto.PasswordChangeRequest;
import com.rslakra.springbootsamples.emailservice.repository.IdentityRepository;
import com.rslakra.springbootsamples.emailservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.transaction.Transactional;

/**
 * @author Rohtash Lakra
 * @created 11/7/24
 * Merged controller for password management (forgot password and change password)
 */
@Controller
public class PasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Display the forgot password page
     *
     * @param model
     * @return
     */
    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage(Model model) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Forgot password page requested");
        }
        return "forgot-password";
    }

    /**
     * Handle forgot password form submission
     *
     * @param email
     * @param model
     * @return
     */
    @PostMapping("/forgotPassword")
    public String handleForgotPassword(String email, Model model) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Forgot password form submitted for email: {}", email);
        }
        // TODO: Implement password reset email sending logic
        model.addAttribute("successMessage", "If an account exists with that email, a password reset link has been sent.");
        return "forgot-password";
    }

    /**
     * Display the change password page (for logged-in users)
     *
     * @param model
     * @return
     */
    @ModelAttribute("passwordChangeForm")
    public PasswordChangeRequest passwordChange() {
        return new PasswordChangeRequest();
    }

    @GetMapping("/user/change-password")
    public String displayPasswordChange(Model model) {
        return "change-password";
    }

    /**
     * Handle change password form submission
     *
     * @param form
     * @param result
     * @return
     */
    @PostMapping("/user/change-password")
    @Transactional
    public String handlePasswordChange(@ModelAttribute("passwordChangeForm") @Validated PasswordChangeRequest form,
                                       BindingResult result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoggedInUserRequest loggedInUserRequest = (LoggedInUserRequest) auth.getPrincipal();
        IdentityDO user = identityRepository.findById(loggedInUserRequest.getUserObjectId()).orElse(null);
        
        if (user == null) {
            return "redirect:/login?notLoggedIn";
        } else if (form.getNewPassword().isEmpty()) {
            return "change-password";
        } else if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null,
                               "You must enter the same password twice in order to confirm it.");
            LOGGER.info("Confirmation password mismatch.");
            return "change-password";
        } else if (!passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
            result.rejectValue("currentPassword", null, "Current password is incorrect.");
            return "change-password";
        }

        String updatedPassword = passwordEncoder.encode(form.getConfirmPassword());
        userService.updatePassword(updatedPassword, user.getId());

        return "redirect:/?changeSuccess";
    }
}

