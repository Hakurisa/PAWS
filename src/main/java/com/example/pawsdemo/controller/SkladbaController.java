package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.SkladbaService;
import com.example.pawsdemo.services.UmelecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class SkladbaController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    @Autowired
    private SkladbaService service;

    @Autowired
    private UzivatelRepository userRepo;

    @Autowired
    private UmelecService umelecService;
    @GetMapping("/skladba/new")
    public String uploadView(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        SkladbaDtoIn skladba = new SkladbaDtoIn();
        List<AlbumEntity> albums = umelecService.getUmelecAlbums(umelecId);
        model.addAttribute("albums", albums);
        model.addAttribute("skladba", skladba);
        return "skladbaUpload";
    }

    @PostMapping("/skladba/new")
    public String uploadSong(@ModelAttribute SkladbaDtoIn skladba, @RequestParam("song") MultipartFile song, @RequestParam("album") Integer albumId, Principal principal, RedirectAttributes redirectAttributes) throws IOException {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        service.saveSong(skladba, song, albumId);
        return "index";
    }
}
