package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.*;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    private UserDetailsService userDetService;
    private UzivatelRepository uzivatelRepo;
    private BURepository buRepo;
    private UmelecRepository umelecRepo;
    private UzivatelService uzivatelService;
    private PlaylistRepository playlistRepo;
    private AlbumService albumService;
    private PlaylistService playlistService;
    private RecenzeService recenzeService;

    @Autowired
    public MainController(@Qualifier("uzivatelService") UserDetailsService userDetService,
                          UzivatelRepository uzivatelRepo,
                          BURepository buRepo,
                          UmelecRepository umelecRepo,
                          UzivatelService uzivatelService,
                          PlaylistRepository playlistRepo,
                          AlbumService albumService,
                          PlaylistService playlistService,
                          RecenzeService recenzeService) {
        this.userDetService = userDetService;
        this.uzivatelRepo = uzivatelRepo;
        this.buRepo = buRepo;
        this.umelecRepo = umelecRepo;
        this.uzivatelService = uzivatelService;
        this.playlistRepo = playlistRepo;
        this.albumService = albumService;
        this.playlistService = playlistService;
        this.recenzeService = recenzeService;
    }

    private UzivatelEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return uzivatelRepo.findUzivatelByUsername(username);
    }

    @GetMapping({"/index" , "/"})
    public String index(Model model, Principal principal) {
        Integer buId = getCurrentUser().getBeznyuzivatelId();
        Integer umelecId = getCurrentUser().getUmelecId();

        if (buId != null) {
            model.addAttribute("isBu", true);
        }
        if (umelecId != null) {
            model.addAttribute("isUmelec", true);
        }

        UserDetails userDet = userDetService.loadUserByUsername(principal.getName());
        List<PlaylistEntity> playlists = playlistService.getAllPlaylists();
        List<AlbumEntity> albums = albumService.getAllAlbums();
        model.addAttribute("userdetail", userDet);
        model.addAttribute("playlists", playlists);
        model.addAttribute("albums", albums);
        return "index";
    }

    @GetMapping("/artistProfile")
    public String artistProfile(Model model) {
        int umelecId = getCurrentUser().getUmelecId();
        UmelecEntity umelecInfo = umelecRepo.findUmelecEntityByUmelecId(umelecId);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("currentUmelec", umelecInfo);
        return "artistProfile";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model, Principal principal) {
        String username = principal.getName();
        int buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("currentBU", bu);
        return "userProfile";
    }

    //TODO: Post for profile updating

    @PostMapping("/userProfile")
    public String userProfile(@ModelAttribute("uzivatel") UzivatelDtoIn uzivatelDtoIn, @ModelAttribute("beznyuzivatel") BeznyUzivatelDotIn beznyUzivatelDotIn, @RequestParam("profilepicture") MultipartFile profilePicture, Principal principal, RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        Integer userId = uzivatelRepo.getUzivatelIdOfUzivatel(username);


        if (buId == null || userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any ordinary user account.");
            return "redirect:/index";
        }

        uzivatelService.updateProfile(uzivatelDtoIn, userId, beznyUzivatelDotIn, buId, profilePicture);
        return "redirect:/userProfile";
    }
}
