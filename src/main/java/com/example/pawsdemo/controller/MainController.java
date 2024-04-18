package com.example.pawsdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/artistProfile")
    public String artistProfile() {
        return "artistProfile";
    }
    @GetMapping("/userProfile")
    public String userProfile() {
        return "userProfile";
    }

    @GetMapping("/playlist")
    public String playlist() {
        return "playlist";
    }
}