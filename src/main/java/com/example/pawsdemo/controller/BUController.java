package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.BUService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class BUController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    private BURepository buRepo;

    private UzivatelRepository uzivatelRepo;

    private PlaylistRepository playlistRepo;

    @Autowired
    public BUController(BURepository buRepo, UzivatelRepository uzivatelRepo, PlaylistRepository playlistRepo) {
        this.buRepo = buRepo;
        this.uzivatelRepo = uzivatelRepo;
        this.playlistRepo = playlistRepo;
    }

    @GetMapping("/playlist/new")
    public String createNewPlayist(Model model, WebRequest requestInfo){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UzivatelEntity currentUser = uzivatelRepo.findUzivatelByUsername(username);
        int buId = currentUser.getBeznyuzivatelId();
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);
        PlaylistDtoIn playlist = new PlaylistDtoIn();
        model.addAttribute("creator", bu);
        model.addAttribute("playlist", playlist);
        return "createPlaylist";
    }

    // public ModelAndView createPlaylist(@ModelAttribute("playlist") @Valid  PlaylistDtoIn playlistDto){

    // }

}
