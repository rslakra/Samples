package com.rslakra.springbootservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rohtash Lakra
 * @created 8/21/21 5:46 AM
 */
@Controller
public class HomeController {

    /**
     * Index Page
     *
     * @return
     */
    @GetMapping({"/", "index"})
    public String indexPage() {
        return "index";
    }

}
