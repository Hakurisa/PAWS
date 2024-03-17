package com.example.pawsdemo.controlller;

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
}
