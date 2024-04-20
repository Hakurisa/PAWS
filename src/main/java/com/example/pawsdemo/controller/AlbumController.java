package com.example.pawsdemo.controller;


import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.AlbumService;
import com.example.pawsdemo.services.UzivatelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private AlbumService service;
    @Autowired
    private UzivatelRepository userRepo;
    @GetMapping("/album/new")
    public String uploadView(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        AlbumDtoIn albumDtoIn = new AlbumDtoIn();
        model.addAttribute("album", albumDtoIn);
        return "albumUpload";

    }

    @PostMapping("/album/new")
    public String createAlbum(@ModelAttribute AlbumDtoIn album, @RequestParam("coverimage") MultipartFile coverImage, Principal principal, RedirectAttributes redirectAttributes) throws IOException {
        String username = principal.getName();
        logger.info("entering album zone - control");
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        service.addNewAlbum(album, coverImage, umelecId);
        return "/";
    }
}
