package com.example.pawsdemo.controller;

import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.AlbumService;
import com.example.pawsdemo.services.SkladbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {

    private SkladbaRepository skladbaRepository;
    private AlbumService albumService;
    private SkladbaService skladbaService;
    private AlbumRepository albumRepository;
    private UzivatelRepository uzivatelRepo;

    @Autowired
    public SearchController(AlbumService albumService, SkladbaService skladbaService, AlbumRepository albumRepository, SkladbaRepository skladbaRepository, UzivatelRepository uzivatelRepo) {
        this.albumService = albumService;
        this.skladbaService = skladbaService;
        this.albumRepository = albumRepository;
        this.skladbaRepository = skladbaRepository;
        this.uzivatelRepo = uzivatelRepo;
    }

    @GetMapping("search")
    public String search(Model model, @RequestParam String name, @RequestParam String type, Principal principal) {
        String username = principal.getName();
        Integer buId = uzivatelRepo.getBeznyUzivatelIdOfUzivatel(username);
        Integer umelecId = uzivatelRepo.getUmelecIdOfUzivatel(username);

        if(name.isBlank()){
            return "redirect:/index";
        }

        if (buId != null) {
            model.addAttribute("isBu", true);
            model.addAttribute("userId", buId);
        }
        if (umelecId != null) {
            model.addAttribute("isUmelec", true);
            model.addAttribute("userId", umelecId);
        }

        if(type.equals("album")) {
            List<AlbumEntity> albums = albumRepository.findAlbumEntitiesByPartialNazev(name);
            model.addAttribute("isAlbum", true);
            model.addAttribute("albums", albums);
            return "search";
        } else if(type.equals("song")) {
            List<SkladbaEntity> skladbaEntities = skladbaRepository.findSkladbaEntitiesByPartialJmeno(name);
            //for every skladba in skladba entities:
            for(SkladbaEntity skladba : skladbaEntities) {
                AlbumEntity album = skladbaService.getAlbumBySkladbaId(skladba.getSkladbaId());
                String umelec = albumService.getUmelecName(album.getAlbumId());
                skladba.setUmelec(umelec);
            }
            //associate the "umelec" with the corresponding skladba entity
            model.addAttribute("isSkladba", true);
            model.addAttribute("skladby", skladbaEntities);
            return "search";
        }
        return "redirect:/index";

    }
}
