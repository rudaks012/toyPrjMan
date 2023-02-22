package com.toyprj.toyman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        System.out.println("Hello, World!");
        return "index";
    }

}
