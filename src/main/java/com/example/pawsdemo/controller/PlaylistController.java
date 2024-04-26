package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
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
import java.security.Principal;
import java.util.Optional;

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
    public String showPlaylist(Model model, @PathVariable int id, Principal principal){
        String username = principal.getName();
        Integer currentBu = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        PlaylistDtoIn selectedPlaylist = playlistService.getPlaylistDtoById(id);

        model.addAttribute("beznyuzivatel", currentBu);
        model.addAttribute("playlist", selectedPlaylist);
        return "playlist";
    }

    @GetMapping("playlist/{id}/edit")
    public String showAlbumEditForm(@PathVariable Integer id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/playlist/" + id;
        }

        PlaylistDtoIn playlist = playlistService.getPlaylistDtoById(id);
        model.addAttribute("playlist", playlist);
        return "playlistEdit";
    }

    @PostMapping("playlist/{id}/edit")
    public String updateAlbum(@PathVariable Integer id, PlaylistDtoIn playlistDtoIn, @RequestParam("coverimage") MultipartFile coverImage, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);

        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }

        playlistService.update(playlistDtoIn, coverImage, id);
        return "redirect:/playlist/" + id;
    }

    @PostMapping("playlist/new")
    public String createPlaylist(@ModelAttribute PlaylistDtoIn playlistDto, @RequestParam("coverimage") MultipartFile coverimage, Principal principal, RedirectAttributes redirectAttributes) throws IOException {
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);

        if(buId == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Žádný uživatelský účet nebyl nalezen");
            return "redirect:/index";
        }

        playlistService.newPlaylist(playlistDto, coverimage, bu.getJmeno(), buId);
        return "redirect:/index";
    }

    @DeleteMapping("playlist/{id}")
    public String deleteAlbum(@PathVariable Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "No outsiders");
            return "redirect:/index";
        }

        playlistService.deletePlaylist(id);
        return "redirect:/index";
    }

    @PutMapping("album/{albumId}/add/skladba")
    public String addSongToPlaylist(@PathVariable Integer albumId, @RequestParam("pickedplaylist") Integer playlistid, @RequestParam("pickedskladba") Integer skladbaid){

        playlistService.addSongtoUsersPlaylist(playlistid, skladbaid);

        return "redirect:/album/" + albumId;
    }

}
