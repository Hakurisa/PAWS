package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.services.SkladbaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SkladbaController {

    private SkladbaService service;
    @GetMapping("/skladba/new")
    public String uploadView(Model model) {
        SkladbaDtoIn skladba = new SkladbaDtoIn();
        model.addAttribute("skladba", skladba);
        return "skladbaUpload";
    }

    @PostMapping("/skladba/new")
    public String uploadSong(Model model) {
        return "TODO";
    }
}
