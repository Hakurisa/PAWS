package com.example.pawsdemo.controller;


import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private AlbumService service;
    private UzivatelRepository userRepo;

    @Autowired
    public AlbumController(AlbumService service, UzivatelRepository userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

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
        return "redirect:/index";
    }

    @GetMapping("album/{id}")
    public String showAlbum(@PathVariable Integer id, Model model) {
        AlbumDtoIn album = service.getAlbumDtoById(id);
        logger.info("bruh moment in album shows");
        model.addAttribute("album", album);
        return "album";
    }

    @GetMapping("album/{id}/edit")
    public String showAlbumEditForm(@PathVariable Integer id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        //TODO: check if umelec is the owner of this album
        AlbumDtoIn album = service.getAlbumDtoById(id);
        logger.info("bruh moment in album edit render");
        logger.info("Published:" + album.getPublikovano());
        model.addAttribute("album", album);
        return "albumEdit";
    }

    @PostMapping("album/{id}/edit")
    public String updateAlbum(@PathVariable Integer id, AlbumDtoIn albumDtoIn, @RequestParam("coverimage") MultipartFile coverImage, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        //TODO: check if umelec is the owner of this album
        service.updateAlbum(albumDtoIn, coverImage, id);
        return "redirect:/index";
    }
    @DeleteMapping("album/{id}")
    public String deleteAlbum(@PathVariable Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        //TODO: check if umelec is the owner of this album
        service.deleteAlbum(id);
        return "redirect:/index";
    }
}
