package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.services.SkladbaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class SkladbaController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    @Autowired
    private SkladbaService service;
    @GetMapping("/skladba/new")
    public String uploadView(Model model) {
        SkladbaDtoIn skladba = new SkladbaDtoIn();
        model.addAttribute("skladba", skladba);
        return "skladbaUpload";
    }

    @PostMapping("/skladba/new")
    public String uploadSong(@ModelAttribute SkladbaDtoIn skladba, @RequestParam("song") MultipartFile song, @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
        service.saveSong(skladba, song, coverImage);
        return "skladbaUpload";
    }
}
