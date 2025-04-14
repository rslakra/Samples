package com.rslakra.springbootservice.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rohtash Lakra
 * @created 5/8/23 12:34 PM
 */
@RestController
@RequestMapping("/rest")
public class HomeRestController {

    @GetMapping({"/", "index", "home"})
    public String indexPage() {
        return "Hello, SpringBoot Service!";
    }

}
