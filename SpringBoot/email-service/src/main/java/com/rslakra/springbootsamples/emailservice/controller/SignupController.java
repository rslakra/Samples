package com.rslakra.springbootsamples.emailservice.controller;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.springbootsamples.emailservice.Constants;
import com.rslakra.springbootsamples.emailservice.domain.user.IdentityDO;
import com.rslakra.springbootsamples.emailservice.domain.user.UserInfo;
import com.rslakra.springbootsamples.emailservice.dto.UserRequest;
import com.rslakra.springbootsamples.emailservice.repository.IdentityRepository;
import com.rslakra.springbootsamples.emailservice.service.UserInfoService;
import com.rslakra.springbootsamples.emailservice.service.UserService;
import com.rslakra.springbootsamples.emailservice.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 11/7/24
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private IdentityRepository identityRepository;

    /**
     * Display the signup page
     *
     * @return
     */
    @GetMapping(value = {"", "/"})
    public String showSignupPage() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Signup page requested");
        }
        return "signup";
    }

    /**
     * Handle signup form submission
     *
     * @param request
     * @param attrs
     * @return
     */
    @PostMapping(value = {"", "/"})
    public String signupUser(HttpServletRequest request, RedirectAttributes attrs) {
        LOGGER.debug("+signupUser()");
        
        String userName = request.getParameter("user_name") != null ? request.getParameter("user_name").trim() : "";
        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
        String firstName = request.getParameter("first_name") != null ? request.getParameter("first_name").trim() : "";
        String lastName = request.getParameter("last_name") != null ? request.getParameter("last_name").trim() : "";
        String password = request.getParameter("password") != null ? request.getParameter("password").trim() : "";
        String confirmPassword = request.getParameter("confirm_password") != null ? request.getParameter("confirm_password").trim() : "";

        Map<String, String> errors = new HashMap<>();
        String usernameError = "";
        String emailError = "";
        String passwordError = "";

        // Validate required fields
        if (BeanUtils.isEmpty(userName)) {
            usernameError = Constants.MSG_REQUIRED_USERNAME;
        }
        if (BeanUtils.isEmpty(email)) {
            emailError = "Email is required!";
        }
        if (BeanUtils.isEmpty(firstName)) {
            errors.put("firstName_error", "First name is required!");
        }
        if (BeanUtils.isEmpty(lastName)) {
            errors.put("lastName_error", "Last name is required!");
        }
        if (BeanUtils.isEmpty(password)) {
            passwordError = Constants.MSG_REQUIRED_PASSWORD;
        }
        if (BeanUtils.isEmpty(confirmPassword)) {
            errors.put("confirmPassword_error", "Please confirm your password!");
        }

        // Validate password match
        if (!BeanUtils.isEmpty(password) && !BeanUtils.isEmpty(confirmPassword) && !password.equals(confirmPassword)) {
            passwordError = "Passwords do not match!";
        }

        // Validate input format (XSS protection)
        if (!BeanUtils.isEmpty(userName)) {
            if (!SecurityUtils.isValidUsername(userName)) {
                usernameError = Constants.MSG_BAD_LOGIN_INPUT;
            } else if (!SecurityUtils.isSafeInput(userName, 50)) {
                usernameError = Constants.MSG_BAD_LOGIN_INPUT;
            }
        }

        if (!BeanUtils.isEmpty(email)) {
            if (!SecurityUtils.isValidEmail(email)) {
                emailError = "Invalid email format!";
            }
        }

        // Check if username already exists
        if (!BeanUtils.isEmpty(userName)) {
            UserInfo existingUser = userInfoService.getUserByName(userName);
            if (existingUser != null) {
                usernameError = "Username already exists!";
            }
        }

        // Check if email already exists
        if (!BeanUtils.isEmpty(email)) {
            IdentityDO existingIdentity = identityRepository.findByEmail(email);
            if (existingIdentity != null) {
                emailError = "Email already exists!";
            }
        }

        // If there are validation errors, return to signup page with errors
        if (!usernameError.isEmpty() || !emailError.isEmpty() || !passwordError.isEmpty() || !errors.isEmpty()) {
            if (!usernameError.isEmpty()) {
                attrs.addFlashAttribute("username_error", usernameError);
            }
            if (!emailError.isEmpty()) {
                attrs.addFlashAttribute("email_error", emailError);
            }
            if (!passwordError.isEmpty()) {
                attrs.addFlashAttribute("password_error", passwordError);
            }
            if (!errors.isEmpty()) {
                attrs.addFlashAttribute("errors", errors);
            }
            LOGGER.debug("-signupUser(), validation errors found");
            return "redirect:/signup";
        }

        // Create user request
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName(userName);
        userRequest.setEmail(email);
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setPassword(password);
        // Use email as ID since phone number is not provided in signup form
        userRequest.setId(email);
        userRequest.setPhoneNumber(""); // Phone number not required for signup

        try {
            // Save the user
            String userId = userService.saveUser(userRequest);
            LOGGER.info("User created successfully with ID: {}", userId);
            attrs.addFlashAttribute("successMessage", "Account created successfully! Please login.");
            LOGGER.debug("-signupUser(), user created successfully");
            return "redirect:" + Constants.URL_LOGIN;
        } catch (Exception e) {
            LOGGER.error("Error creating user: ", e);
            attrs.addFlashAttribute("errorMessage", "An error occurred while creating your account. Please try again.");
            LOGGER.debug("-signupUser(), error occurred");
            return "redirect:/signup";
        }
    }
}

