package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.RecenzeEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.services.RecenzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class RezenzeController {

    UzivatelRepository uzivatelRepo;
    BURepository buRepo;
    SkladbaRepository skladbaRepo;
    UmelecRepository umelecRepo;
    AlbumRepository albumRepo;
    RecenzeService recenzeService;

    @Autowired
    public RezenzeController(UzivatelRepository uzivatelRepo, BURepository buRepo, SkladbaRepository skladbaRepo, UmelecRepository umelecRepo, AlbumRepository albumRepo, RecenzeService recenzeService) {
        this.uzivatelRepo = uzivatelRepo;
        this.buRepo = buRepo;
        this.skladbaRepo = skladbaRepo;
        this.umelecRepo = umelecRepo;
        this.albumRepo = albumRepo;
        this.recenzeService = recenzeService;
    }

    @PostMapping("album/{albumId}")
    public String createAlbumRecenze(@PathVariable Integer albumId,
                                   RecenzeDtoIn recenzeDtoIn,
                                   @RequestParam("pocethvezd") int pocetHvezd,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);

        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/index";
        }

        try {
            recenzeService.createRecenze(recenzeDtoIn, "isAlbum", buId, albumId, pocetHvezd);
            return "redirect:/album/" + albumId;
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to create new review");
            return "redirect:/album" + albumId;
        }
    }

    @PostMapping("artist/{umelecId}")
    public String createUmelecRecenze(@PathVariable Integer umelecId,
                                     RecenzeDtoIn recenzeDtoIn,
                                     @RequestParam("pocethvezd") int pocetHvezd,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);

        if (buId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/index";
        }

        try {
            recenzeService.createRecenze(recenzeDtoIn, "isUmelec", buId, umelecId, pocetHvezd);
            return "redirect:/artist/" + umelecId;
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to create new review");
            return "redirect:/artist/" + umelecId;
        }
    }
}
