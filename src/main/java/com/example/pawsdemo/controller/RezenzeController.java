package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.dotIn.UmelecDtoIn;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.services.RecenzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class RezenzeController {

    UzivatelRepository uzivatelRepo;
    BURepository buRepo;
    SkladbaRepository skladbaRepo;
    UmelecRepository umelecRepo;
    AlbumRepository albumRepo;
    RecenzeService service;

    @Autowired
    public RezenzeController(UzivatelRepository uzivatelRepo, BURepository buRepo, SkladbaRepository skladbaRepo, UmelecRepository umelecRepo, AlbumRepository albumRepo, RecenzeService service) {
        this.uzivatelRepo = uzivatelRepo;
        this.buRepo = buRepo;
        this.skladbaRepo = skladbaRepo;
        this.umelecRepo = umelecRepo;
        this.albumRepo = albumRepo;
        this.service = service;
    }

    /* @PostMapping("/recenze/new")
    public String newRecenze(@PathVariable Integer id, @ModelAttribute RecenzeDtoIn recenzeDto, @RequestParam Map<String,String> formDate, Principal principal){
        String typRecenze = formDate.get("typRecenze");

        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        if(typRecenze.equals("isSkladba")){

        }
        if(typRecenze.equals("isAlbum")){

        }
        if(typRecenze.equals("isSkladba")){

        }
        if(typRecenze.equals("isKapela")){

        }
        service.createRecenze(recenzeDto, typRecenze, buId, entityId);
        return "";
    } */
}
