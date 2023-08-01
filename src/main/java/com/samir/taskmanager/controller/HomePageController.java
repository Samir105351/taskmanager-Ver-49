package com.samir.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class HomePageController {
    @GetMapping("/")
    public String homePage() {
        return "home/index";
    }
}
