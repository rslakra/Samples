package com.rslakra.springbootjsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    /**
     * @return
     */
    @GetMapping("/settings")
    public String settings() {
        return "profile/settings";
    }

    /**
     * @return
     */
    @GetMapping("/logout")
    public RedirectView logout() {
        return new RedirectView("/", true);
    }
}

