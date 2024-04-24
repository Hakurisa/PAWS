package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.ZanrEntity;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.repository.ZanrRepository;
import com.example.pawsdemo.services.SkladbaService;
import com.example.pawsdemo.services.UmelecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class SkladbaController {

    private static final Logger logger = LoggerFactory.getLogger(SkladbaController.class);

    @Autowired
    private SkladbaService service;

    @Autowired
    private UzivatelRepository userRepo;

    @Autowired
    private UmelecService umelecService;

    @Autowired
    private ZanrRepository zanrRepository;
    @GetMapping("/skladba/new")
    public String uploadView(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        SkladbaDtoIn skladba = new SkladbaDtoIn();
        List<AlbumEntity> albums = umelecService.getUmelecAlbums(umelecId);
        List<ZanrEntity> zanry = (List<ZanrEntity>) zanrRepository.findAll();
        model.addAttribute("zanry", zanry);
        model.addAttribute("albums", albums);
        model.addAttribute("skladba", skladba);
        return "skladbaUpload";
    }

    @PostMapping("/skladba/new")
    public String uploadSong(@ModelAttribute SkladbaDtoIn skladba, @RequestParam("song") MultipartFile song, @RequestParam("album") Integer albumId, @RequestParam("zanr") Integer zanrId, Principal principal, RedirectAttributes redirectAttributes) throws IOException {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        service.saveSong(skladba, song, albumId, zanrId);
        return "redirect:/index";
    }

    @GetMapping("skladba/{id}/edit")
    public String showSkladbaEditForm(@PathVariable Integer id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        //TODO: check if umelec is the owner of this album
        SkladbaDtoIn skladba = service.getSkladbaDtoById(id);
        logger.info("bruh moment in skladba edit render");
        logger.info("Skladba ID:" + skladba.getSkladbaId());
        model.addAttribute("skladba", skladba);
        return "skladbaEdit";
    }

    @PostMapping("skladba/{id}/edit")
    public String updateSkladba(@PathVariable Integer id, SkladbaDtoIn skladbaDtoIn, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        //TODO: check if umelec is the owner of this album
        service.updateSkladba(skladbaDtoIn, id);
        return "redirect:/index";
    }
    @DeleteMapping("skladba/{id}")
    public String deleteSkladba(@PathVariable Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        Integer umelecId = userRepo.getUmelecIdOfUzivatel(username);
        if (umelecId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not associated with any artist.");
            return "redirect:/index";
        }
        //TODO: check if umelec is the owner of this album
        service.deleteSkladba(id);
        return "redirect:/index";
    }
}
