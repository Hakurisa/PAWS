package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyUzivatelPlaylistEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.PlaylistService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.Map;

@Controller
public class BUController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    private BURepository buRepo;

    private UzivatelRepository uzivatelRepo;

    private PlaylistRepository playlistRepo;

    private PlaylistService playlistService;

    @Autowired
    public BUController(BURepository buRepo, UzivatelRepository uzivatelRepo, PlaylistRepository playlistRepo, PlaylistService playlistService) {
        this.buRepo = buRepo;
        this.uzivatelRepo = uzivatelRepo;
        this.playlistRepo = playlistRepo;
        this.playlistService = playlistService;
    }

    private UzivatelEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return uzivatelRepo.findUzivatelByUsername(username);
    }

    @GetMapping("/playlist/new")
    public String createNewPlayist(Model model, WebRequest requestInfo){
        int buId = getCurrentUser().getBeznyuzivatelId();
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);

        PlaylistDtoIn playlist = new PlaylistDtoIn();

        model.addAttribute("creator", bu);
        model.addAttribute("playlist", playlist);
        return "createPlaylist";
    }

    @PostMapping("playlist/new")
    public ModelAndView createPlaylist(@ModelAttribute("playlist") PlaylistDtoIn playlistDto, @RequestParam Map<MultipartFile, MultipartFile> formDate){
        int buId = getCurrentUser().getBeznyuzivatelId();
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);
        MultipartFile coverImage = formDate.get("coverImage");

        final PlaylistEntity uploadPlaylist = playlistService.newPlaylist(playlistDto, coverImage, bu.getJmeno());
        final BeznyUzivatelPlaylistEntity uploadBuPlaylist = playlistService.newBUToPlaylistConnection(buId);
        return new ModelAndView("createPlaylist", "playlist", playlistDto);
    }

}
