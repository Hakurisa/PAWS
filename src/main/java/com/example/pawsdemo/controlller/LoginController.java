package com.example.pawsdemo.controlller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String viewLoginPage(ModelMap map){
        return "login";
    }
}
