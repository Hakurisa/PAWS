package com.example.pawsdemo.controller;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.dotIn.api.AlbumApiDtoIn;
import com.example.pawsdemo.dotIn.api.PlaylistApiDtoIn;
import com.example.pawsdemo.models.*;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.services.AlbumService;
import com.example.pawsdemo.services.PlaylistService;
import com.example.pawsdemo.services.RecenzeService;
import com.example.pawsdemo.services.SkladbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/api")
public class APIController {
    private AlbumService service;
    private RecenzeService recenzeService;
    private UzivatelRepository userRepo;
    private SkladbaService skladbaService;
    private SkladbaRepository skladbaRepo;
    private PlaylistService playlistService;
    private PlaylistRepository playlistRepo;
    private BURepository buRepo;

    @Autowired
    public APIController(AlbumService service, RecenzeService recenzeService, UzivatelRepository userRepo, SkladbaService skladbaService, SkladbaRepository skladbaRepo, PlaylistService playlistService, PlaylistRepository playlistRepo, BURepository buRepo) {
        this.service = service;
        this.recenzeService = recenzeService;
        this.userRepo = userRepo;
        this.skladbaService = skladbaService;
        this.skladbaRepo = skladbaRepo;
        this.playlistService = playlistService;
        this.playlistRepo = playlistRepo;
        this.buRepo = buRepo;
    }

    @GetMapping("/album/{id}")
    public AlbumApiDtoIn showAlbum(@PathVariable Integer id) {
        AlbumDtoIn album = service.getAlbumDtoById(id);
        List<SkladbaEntity> skladby = skladbaService.getAllSkladbyByAlbumId(id);
        List<SkladbaDtoIn> skladbyDto = new ArrayList<>();
        String umelec = service.getUmelecName(id);

        for (SkladbaEntity skladba : skladby) {
            SkladbaDtoIn skladbaDtoIn = new SkladbaDtoIn();
            skladbaDtoIn.setSkladbaId(skladba.getSkladbaId());
            skladbaDtoIn.setJmeno(skladba.getJmeno());
            skladbaDtoIn.setAudioslozka(skladba.getAudioslozka());
            skladbaDtoIn.setDelka(skladba.getDelka());
            skladbaDtoIn.setCoverimage(skladba.getCoverimage());
            skladbaDtoIn.setPocetprehrani(skladba.getPocetprehrani());
            skladbyDto.add(skladbaDtoIn);
        }


        AlbumApiDtoIn albumApiDtoIn = new AlbumApiDtoIn(); //takes albumdtoin, skladbadtoin
        albumApiDtoIn.setAlbum(album);
        albumApiDtoIn.setSkladby(skladbyDto);
        albumApiDtoIn.setUmelec(umelec);
        return albumApiDtoIn;
    }

    @GetMapping("/playlist/{id}")
    public PlaylistApiDtoIn showPlaylist(@PathVariable Integer id) {
        PlaylistDtoIn playlist = playlistService.getPlaylistDtoById(id);
        List<SkladbaEntity> skladby = skladbaRepo.findByPlaylistId(id);
        List<SkladbaDtoIn> skladbyDto = new ArrayList<>();
        List<String> umelci = new ArrayList<>();

        for (SkladbaEntity skladba : skladby) {
            SkladbaDtoIn skladbaDtoIn = new SkladbaDtoIn();
            AlbumEntity album = skladbaService.getAlbumBySkladbaId(skladba.getSkladbaId());
            String umelec = service.getUmelecName(album.getAlbumId());
            skladbaDtoIn.setSkladbaId(skladba.getSkladbaId());
            skladbaDtoIn.setJmeno(skladba.getJmeno());
            skladbaDtoIn.setAudioslozka(skladba.getAudioslozka());
            skladbaDtoIn.setDelka(skladba.getDelka());
            skladbaDtoIn.setCoverimage(skladba.getCoverimage());
            skladbaDtoIn.setPocetprehrani(skladba.getPocetprehrani());
            umelci.add(umelec);
            skladbyDto.add(skladbaDtoIn);
        }

        PlaylistApiDtoIn playlistApiDtoIn = new PlaylistApiDtoIn();
        playlistApiDtoIn.setPlaylist(playlist);
        playlistApiDtoIn.setSkladby(skladbyDto);
        playlistApiDtoIn.setUmelci(umelci);
        return playlistApiDtoIn;
    }
}
