package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.UmelecRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class MainController {
    private UserDetailsService userDetService;
    private UzivatelRepository uzivatelRepo;
    private BURepository buRepo;
    private UmelecRepository umelecRepo;

    @Autowired
    public MainController(@Qualifier("uzivatelService") UserDetailsService userDetService, UzivatelRepository uzivatelRepo, BURepository buRepo, UmelecRepository umelecRepo) {
        this.userDetService = userDetService;
        this.uzivatelRepo = uzivatelRepo;
        this.buRepo = buRepo;
        this.umelecRepo = umelecRepo;
    }

    private UzivatelEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return uzivatelRepo.findUzivatelByUsername(username);
    }

    @GetMapping("/index")
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
        model.addAttribute("userdetail", userDet);
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
    public String userProfile(Model model) {
        int buId = getCurrentUser().getBeznyuzivatelId();
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(buId);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("currentBU", bu);
        return "userProfile";
    }

    //TODO: Post for profile updating

    @GetMapping("/playlist")
    public String playlist() {
        return "playlist";
    }
}
