package com.example.pawsdemo.controlller;

import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    /* @GetMapping("/login/registration")
    public String registration(WebRequest requestUser, Model model) {
        UzivatelDtoIn uzivatel = new UzivatelDtoIn();
        model.addAttribute("uzivatel", uzivatel);
        return "registration";
    } */
}
