package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class PlaylistController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    private BURepository buRepo;

    private UzivatelRepository uzivatelRepo;

    private PlaylistRepository playlistRepo;

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController(BURepository buRepo, UzivatelRepository uzivatelRepo, PlaylistRepository playlistRepo, PlaylistService playlistService) {
        this.buRepo = buRepo;
        this.uzivatelRepo = uzivatelRepo;
        this.playlistRepo = playlistRepo;
        this.playlistService = playlistService;
    }

    private Integer currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UzivatelEntity currentUser = uzivatelRepo.findUzivatelByUsername(username);
        return currentUser.getBeznyuzivatelId();
    }

    @GetMapping("/playlist/new")
    public String createNewPlayist(Model model){
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(currentUser());
        PlaylistDtoIn playlist = new PlaylistDtoIn();

        model.addAttribute("creator", bu);
        model.addAttribute("playlist", playlist);
        return "createPlaylist";
    }

    @GetMapping("playlist/{id}")
    public String showPlaylist(Model model, @PathVariable int id){
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(currentUser());
//        PlaylistEntity selectedPlaylist =
        model.addAttribute("beznyuzivatel", bu);
//        model.addAttribute("playlist", selectedPlaylist);
        return "showPlaylist";
    }

    @PostMapping("playlist/new")
    public String createPlaylist(@ModelAttribute PlaylistDtoIn playlistDto, @RequestParam("coverimage") MultipartFile coverImage, RedirectAttributes redirectAttributes) throws IOException {
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(currentUser());
        Integer buId = bu.getBeznyuzivatelId();

        if(buId == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Žádný uživatelský účet nebyl nalezen");
            return "redirect:/index";
        }

        playlistService.newPlaylist(playlistDto, coverImage, bu.getJmeno(), buId);
        return "redirect:/index";
    }

}
