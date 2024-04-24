package com.example.pawsdemo.controller;


import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.RecenzeEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.AlbumService;
import com.example.pawsdemo.services.RecenzeService;
import com.example.pawsdemo.services.SkladbaService;
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
import java.util.List;

@Controller
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private AlbumService service;
    private RecenzeService recenzeService;
    private UzivatelRepository userRepo;
    private SkladbaService skladbaService;
    private SkladbaRepository skladbaRepo;

    @Autowired
    public AlbumController(AlbumService service, RecenzeService recenzeService, UzivatelRepository userRepo, SkladbaService skladbaService, SkladbaRepository skladbaRepo) {
        this.service = service;
        this.recenzeService = recenzeService;
        this.userRepo = userRepo;
        this.skladbaService = skladbaService;
        this.skladbaRepo = skladbaRepo;
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
        List<RecenzeEntity> recenzes = recenzeService.getAllRecenzeOfAlbum(id);
        List<SkladbaEntity> skladby = skladbaService.getAllSkladbyByAlbumId(id);

        model.addAttribute("album", album);
        model.addAttribute("recenze", recenzes);
        model.addAttribute("skladby", skladby);
        return "album";
    }

    @PostMapping("album/{id}/review/new")
    public String createNewRecenze(@PathVariable Integer id, RecenzeDtoIn recenzeDtoIn, @RequestParam("pocethvezd") Integer pocetHvezd, Principal principal, RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Integer buId = userRepo.getBeznyUzivatelIdOfUzivatel(username);
        recenzeService.createRecenze(recenzeDtoIn, "isAlbum", buId, id, pocetHvezd);
        return "redirect:/album/{id}";
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
