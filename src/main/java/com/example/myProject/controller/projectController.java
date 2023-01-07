package com.example.myProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class projectController {
    @GetMapping
    public String index() {
        return "index";
    }
}
