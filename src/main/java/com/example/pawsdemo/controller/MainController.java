package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.*;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
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
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        Integer umelecId = uzivatelRepo.getUmelecIdOfUzivatel(username);
        UserDetails userDet = userDetService.loadUserByUsername(principal.getName());

        if (buId != null) {
            List<PlaylistEntity> buplaylists = uzivatelService.getAllPlaylistsByBuId(buId);

            model.addAttribute("isBu", true);
            model.addAttribute("buplaylists", buplaylists);
            model.addAttribute("userId", buId);
        }
        if (umelecId != null) {
            model.addAttribute("isUmelec", true);
            model.addAttribute("userId", umelecId);
        }

        List<PlaylistEntity> playlists = playlistService.getAllPlaylists();
        List<AlbumEntity> albums = albumService.getAllAlbums();
        List<AlbumEntity> publishedAlbums = albumService.getAllAlbumsByStatus((byte) 1);

        model.addAttribute("userdetail", userDet);
        model.addAttribute("playlists", playlists);
        model.addAttribute("albums", albums);
        model.addAttribute("publishedalbum", publishedAlbums);
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

    @GetMapping("artist/{id}")
    public String viewArtist(@PathVariable Integer id, Model model, Principal principal){
        String username = principal.getName();
        UzivatelEntity umelecInfo = uzivatelRepo.findUzivatelByUsername(username);
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        Integer umelecId = uzivatelRepo.getUmelecIdOfUzivatel(username);
        List<AlbumEntity> albums = albumService.getAlbumsByUmelecId(id);
        List<AlbumEntity> publishedAlbums = albumService.getPublishedAlbumsByUmelecId(id);
        if (buId != null) {
            model.addAttribute("isBu", true);
        }
        if (umelecId != null) {
            model.addAttribute("isUmelec", true);
        }

        if(Objects.equals(umelecId, id)) {
            model.addAttribute("loggedInUmelec", true);
        } else {
            model.addAttribute("loggedInUmelec", false);
        }

        UmelecEntity umelec = umelecRepo.findUmelecEntityByUmelecId(id);

        List<RecenzeEntity> recenzes = recenzeService.getRecenzeOfUmelec(id);
        model.addAttribute("albums", albums);
        model.addAttribute("publishedAlbums", publishedAlbums);
        model.addAttribute("umelecInfo", umelecInfo);
        model.addAttribute("umelec", umelec);
        model.addAttribute("recenzes", recenzes);

        return "artist";
    }
}
