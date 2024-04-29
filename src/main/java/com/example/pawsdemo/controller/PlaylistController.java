package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.services.PlaylistService;
import com.example.pawsdemo.services.SkladbaService;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class PlaylistController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    private BURepository buRepo;

    private UzivatelRepository uzivatelRepo;

    private PlaylistRepository playlistRepo;

    private PlaylistService playlistService;
    private SkladbaRepository skladbaRepo;
    private SkladbaService skladbaService;
    private AlbumRepository albumRepo;

    @Autowired
    public PlaylistController(BURepository buRepo,
                              UzivatelRepository uzivatelRepo,
                              PlaylistRepository playlistRepo,
                              PlaylistService playlistService,
                              SkladbaRepository skladbaRepo,
                              SkladbaService skladbaService,
                              AlbumRepository albumRepo) {
        this.buRepo = buRepo;
        this.uzivatelRepo = uzivatelRepo;
        this.playlistRepo = playlistRepo;
        this.playlistService = playlistService;
        this.skladbaRepo = skladbaRepo;
        this.skladbaService = skladbaService;
        this.albumRepo = albumRepo;
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
        Integer umelecId = uzivatelRepo.getUmelecIdOfUzivatel(username);
        if (currentBu != null) {
            model.addAttribute("isBu", true);
            model.addAttribute("userId", currentBu);

        }
        if (umelecId != null) {
            model.addAttribute("isUmelec", true);
            model.addAttribute("userId", umelecId);

        }
        PlaylistDtoIn selectedPlaylist = playlistService.getPlaylistDtoById(id);

        List<SkladbaEntity> skladbas = playlistService.getAllSkladbyByPlaylistId(id);
        model.addAttribute("skladbas", skladbas);

        List<AlbumEntity> albums = albumRepo.findAll();

        model.addAttribute("beznyuzivatel", currentBu);
        model.addAttribute("albums", albums);
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
        String playlistAuthor = playlistService.getCreatorUsername(id);

        if(!username.equals(playlistAuthor)){
            redirectAttributes.addFlashAttribute("errorMessage", "You are not the author.");
            return "redirect:/index";
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

        String playlistAuthor = playlistService.getCreatorUsername(id);

        if(!username.equals(playlistAuthor)){
            redirectAttributes.addFlashAttribute("errorMessage", "You are not the author.");
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
    public String deletePlaylist(@PathVariable Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "No outsiders");
            return "redirect:/index";
        }
        String playlistAuthor = playlistService.getCreatorUsername(id);

        if(!username.equals(playlistAuthor)){
            redirectAttributes.addFlashAttribute("errorMessage", "You are not the author.");
            return "redirect:/index";
        }

        playlistService.deletePlaylist(id);
        return "redirect:/index";
    }

    @PostMapping("album/{albumId}/add/{skladbaId}")
    public String addSongToPlaylist(@PathVariable Integer albumId, @RequestParam("pickedplaylist") String pickedPlaylist, @RequestParam("pickedskladba") Integer skladbaId){

        int playlistId = Integer.parseInt(pickedPlaylist);

        playlistService.addSongtoUsersPlaylist(playlistId, skladbaId);

        return "redirect:/album/" + albumId;
    }

}
