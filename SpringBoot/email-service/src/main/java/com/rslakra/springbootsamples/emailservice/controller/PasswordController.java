package com.rslakra.springbootsamples.emailservice.controller;

import com.rslakra.springbootsamples.emailservice.domain.user.IdentityDO;
import com.rslakra.springbootsamples.emailservice.domain.user.UserInfo;
import com.rslakra.springbootsamples.emailservice.dto.PasswordChangeRequest;
import com.rslakra.springbootsamples.emailservice.repository.IdentityRepository;
import com.rslakra.springbootsamples.emailservice.service.UserInfoService;
import com.rslakra.springbootsamples.emailservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserInfoService userInfoService;

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

    @GetMapping("/change-password")
    public String displayPasswordChange(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = false;
        String username = null;
        String displayName = null;
        
        if (session != null) {
            Object isValidUser = session.getAttribute("isValidUser");
            if (isValidUser != null && (Boolean) isValidUser) {
                isLoggedIn = true;
                username = (String) session.getAttribute("username");
                displayName = (String) session.getAttribute("displayName");
            }
        }
        
        if (!isLoggedIn) {
            return "redirect:/login?notLoggedIn";
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("username", username);
        model.addAttribute("displayName", displayName);
        return "change-password";
    }

    /**
     * Handle change password form submission
     *
     * @param form
     * @param result
     * @return
     */
    @PostMapping("/change-password")
    @Transactional
    public String handlePasswordChange(HttpServletRequest request,
                                       @ModelAttribute("passwordChangeForm") @Validated PasswordChangeRequest form,
                                       BindingResult result, Model model) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = false;
        String username = null;
        String displayName = null;
        
        if (session != null) {
            Object isValidUser = session.getAttribute("isValidUser");
            if (isValidUser != null && (Boolean) isValidUser) {
                isLoggedIn = true;
                username = (String) session.getAttribute("username");
                displayName = (String) session.getAttribute("displayName");
            }
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("username", username);
        model.addAttribute("displayName", displayName);
        
        if (!isLoggedIn) {
            return "redirect:/login?notLoggedIn";
        }
        
        // Get user from session
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo == null || userInfo.getUserName() == null) {
            LOGGER.warn("User not found in session");
            return "redirect:/login?notLoggedIn";
        }
        
        // Get user from database
        UserInfo dbUserInfo = userInfoService.getUserByName(userInfo.getUserName());
        if (dbUserInfo == null) {
            LOGGER.warn("User not found in database: {}", userInfo.getUserName());
            return "redirect:/login?notLoggedIn";
        }
        
        // Get IdentityDO for password update
        IdentityDO user = identityRepository.findById(dbUserInfo.getId()).orElse(null);
        if (user == null) {
            LOGGER.warn("IdentityDO not found for user: {}", dbUserInfo.getId());
            return "redirect:/login?notLoggedIn";
        }
        
        // Validate form
        if (form.getNewPassword() == null || form.getNewPassword().trim().isEmpty()) {
            result.rejectValue("newPassword", null, "New password is required.");
            return "change-password";
        }
        
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null,
                               "You must enter the same password twice in order to confirm it.");
            LOGGER.info("Confirmation password mismatch.");
            return "change-password";
        }
        
        if (!passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
            result.rejectValue("currentPassword", null, "Current password is incorrect.");
            LOGGER.info("Current password mismatch for user: {}", userInfo.getUserName());
            return "change-password";
        }

        // Update password
        String updatedPassword = passwordEncoder.encode(form.getConfirmPassword());
        userService.updatePassword(user.getId(), updatedPassword);
        LOGGER.info("Password updated successfully for user: {}", userInfo.getUserName());

        return "redirect:/?changeSuccess";
    }
}

