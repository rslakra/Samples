package com.rslakra.springbootsamples.emailservice.controller;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.springbootsamples.emailservice.Constants;
import com.rslakra.springbootsamples.emailservice.domain.user.UserInfo;
import com.rslakra.springbootsamples.emailservice.service.UserInfoService;
import com.rslakra.springbootsamples.emailservice.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 8/12/21 3:19 PM
 */
@Controller
public class LoginController {

    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserInfoService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String username;
    private String password;
    private String userNameError;
    private String passwordError;
    private Map<String, String> errors;

    /**
     * Method to render the login page
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLoginPage(HttpServletRequest request) {
        //logs debug payload
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("User Controller is executed!");
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            if ((Object) session.getAttribute("isValidUser") != null) {
                if ((Boolean) session.getAttribute("isValidUser") == true) {
                    if ((Boolean) session.getAttribute("isAdmin") == true) {
                        return "redirect:" + Constants.URL_OWNER_HOME;
                    } else {
                        return "redirect:" + Constants.URL_USER_HOME;
                    }
                }
            }
        }
        return "login";
    }

    /**
     * Method to handle the user authentication request.
     *
     * @param request
     * @param response
     * @param session
     * @param attrs
     * @return
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(HttpServletRequest request, HttpServletResponse response,
                               HttpSession session, RedirectAttributes attrs) {
        LOGGER.debug("+authenticate(%s, %s)", request, attrs);
        username = request.getParameter("user_name").trim();
        password = request.getParameter("password").trim();
        errors = new HashMap<String, String>();
        boolean isAdmin = false;
        String homePage = Constants.URL_USER_HOME;
        // if fields are empty or invalid input return error
        if (!validateLoginForm()) {
            if (!userNameError.trim().isEmpty()) {
                attrs.addFlashAttribute("username_error", userNameError);
            }
            if (!passwordError.trim().isEmpty()) {
                attrs.addFlashAttribute("password_error", passwordError);
            }
            if (errors.size() > 0) {
                attrs.addFlashAttribute("errors", errors);
            }
            return "redirect:" + Constants.URL_LOGIN;
        }

        UserInfo user = userService.getUserByName(username);

        // validate the password using BCrypt (passwords are stored with BCrypt during signup)
        boolean isValidUser = false;
        if (user != null && user.getPassword() != null) {
            // Use BCrypt to match password (passwords are stored with BCrypt during signup)
            isValidUser = passwordEncoder.matches(password, user.getPassword());
        } else {
            // User doesn't exist or password is null - use dummy password check to prevent timing attacks
            passwordEncoder.matches("dummy_password", "$2a$10$dummyHashToPreventTimingAttack");
            isValidUser = false;
        }

        // if password doesn't match return error
        if (!isValidUser || user == null) {
            attrs.addFlashAttribute("errorMessage", Constants.MSG_BAD_LOGIN_INPUT);
            return "redirect:" + Constants.URL_LOGIN;
        }
        if (user.getRoleId() == Constants.ADMIN_ROLE_ID) {
            isAdmin = true;
            homePage = Constants.URL_OWNER_HOME;
        }
        session.setAttribute("user", user);
        session.setAttribute("isValidUser", true);
        session.setAttribute("isAdmin", isAdmin);
        // Encode username for HTML display to prevent XSS
        session.setAttribute("username", SecurityUtils.encodeForHTML(user.getUserName()));

        LOGGER.debug("-authenticate(), homePage=%s", homePage);
        return "redirect:" + homePage;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(HttpServletRequest request, HttpServletResponse response,
                             RedirectAttributes redir) {
        HttpSession session = request.getSession();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0);
        if (session != null) {
            session.removeAttribute("user");
            session.removeAttribute("isValidUser");
            session.removeAttribute("username");
            session.removeAttribute("isAdmin");
        }
        session.invalidate();
        redir.addFlashAttribute("successMessage", Constants.MSG_LOGOUT_SUCCESS);
        return "redirect:" + Constants.URL_LOGIN;
    }

    @RequestMapping(value = "/invalidUser", method = RequestMethod.GET)
    public String logoutInvalidUser(HttpServletRequest request, RedirectAttributes redir) {
        redir.addFlashAttribute("errorMessage", Constants.MSG_INVALID_USER);
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
            session.removeAttribute("isValidUser");
            session.removeAttribute("username");
            session.removeAttribute("isAdmin");
        }
        session.invalidate();
        return "redirect:" + Constants.URL_LOGIN;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(HttpServletRequest request) {
        return Constants.URL_ERROR;
    }

    /**
     * Validate for Empty fields
     *
     * @return
     */
    private boolean validateLoginForm() {
        boolean isValid = true;
        userNameError = "";
        passwordError = "";
        errors.clear();
        if (BeanUtils.isEmpty(username)) {
            isValid = false;
            userNameError = Constants.MSG_REQUIRED_USERNAME;
        }
        if (BeanUtils.isEmpty(password)) {
            isValid = false;
            passwordError = Constants.MSG_REQUIRED_PASSWORD;
        }

        if (isValid) {
            boolean isValidInput = hasValidFormInputs();
            if (!isValidInput) {
                LOGGER.warn("Invalid string is used for login.");
                isValid = false;
                errors.put("invalidUserInput", Constants.MSG_BAD_LOGIN_INPUT);
            }
        }

        return isValid;
    }

    /**
     * Validate for invalid value of text fields (xss attacks)
     *
     * @return true if input is valid and safe
     */
    private boolean hasValidFormInputs() {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        // Check if it's an email or username
        if (username.contains("@")) {
            // Validate as email
            return SecurityUtils.isValidEmail(username);
        } else {
            // Validate as username
            return SecurityUtils.isValidUsername(username) && SecurityUtils.isSafeInput(username, 30);
        }
    }
}
