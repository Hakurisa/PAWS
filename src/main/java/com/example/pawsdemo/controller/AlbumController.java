package com.example.pawsdemo.controller;


import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.models.*;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.services.AlbumService;
import com.example.pawsdemo.services.PlaylistService;
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
import java.util.Map;

@Controller
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private AlbumService service;
    private RecenzeService recenzeService;
    private UzivatelRepository userRepo;
    private SkladbaService skladbaService;
    private SkladbaRepository skladbaRepo;
    private PlaylistService playlistService;
    private PlaylistRepository playlistRepo;
    private BURepository buRepo;

    @Autowired
    public AlbumController(AlbumService service, RecenzeService recenzeService, UzivatelRepository userRepo, SkladbaService skladbaService, SkladbaRepository skladbaRepo, PlaylistService playlistService, PlaylistRepository playlistRepo, BURepository buRepo) {
        this.service = service;
        this.recenzeService = recenzeService;
        this.userRepo = userRepo;
        this.skladbaService = skladbaService;
        this.skladbaRepo = skladbaRepo;
        this.playlistService = playlistService;
        this.playlistRepo = playlistRepo;
        this.buRepo = buRepo;
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
    public String createAlbum(@ModelAttribute AlbumDtoIn album, @RequestParam("coverimage") MultipartFile coverImage, Principal principal, RedirectAttributes redirectAttributes, @RequestParam("copyright") String copyright) throws IOException {
        String username = principal.getName();
        logger.info("entering album zone - control");
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        service.addNewAlbum(album, coverImage, umelecId, copyright);
        return "redirect:/index";
    }

    @GetMapping("album/{id}")
    public String showAlbum(@PathVariable Integer id, Model model, Principal principal) {
        AlbumDtoIn album = service.getAlbumDtoById(id);
        logger.info("bruh moment in album shows");
        List<RecenzeEntity> recenzes = recenzeService.getAllRecenzeOfAlbum(id);
        List<SkladbaEntity> skladby = skladbaService.getAllSkladbyByAlbumId(id);

        String username = principal.getName();
        Integer buId = userRepo.getBeznyUzivatelIdOfUzivatel(username);
        if(buId != null) {
            BeznyuzivatelEntity beznyuzivatel = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);
            String tvurce = beznyuzivatel.getJmeno();
            List<PlaylistEntity> playlists = playlistService.getAllUsersPlaylists(tvurce);
            model.addAttribute("playlists", playlists);
            model.addAttribute("isBu", true);
        }

        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if(umelecId != null){
            model.addAttribute("isUmelec", true);
        }

        model.addAttribute("album", album);
        model.addAttribute("recenzes", recenzes);
        model.addAttribute("skladby", skladby);
        return "album";
    }

    @PostMapping("album/{albumId}")
    public String createNewRecenze(@PathVariable Integer albumId,
                                   RecenzeDtoIn recenzeDtoIn,
                                   @RequestParam("pocethvezd") int pocetHvezd,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Integer buId = userRepo.getBeznyUzivatelIdOfUzivatel(username);

        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/index";
        }

        try {
            recenzeService.createRecenze(recenzeDtoIn, "isAlbum", buId, albumId, pocetHvezd);
            return "redirect:/album/" + albumId;
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to create new review");
            return "redirect:/index";
        }
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
