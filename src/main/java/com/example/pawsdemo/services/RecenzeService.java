package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.RecenzeDotIn;
import com.example.pawsdemo.models.RecenzeEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecenzeService {
    private RecenzeRepository recenzeRepo;
    private BURepository buRepo;
    private AlbumRepository albumRepo;
    private SkladbaRepository skladbaRepo;
    private UmelecRepository umelecRepo;
    //TODO when I will have Repository Kapela from Hakurisa
    // private KapelaRepository kapelaRepo;

    @Autowired
    public RecenzeService(RecenzeRepository recenzeRepo, BURepository buRepo,
                          AlbumRepository albumRepo, SkladbaRepository skladbaRepo, UmelecRepository umelecRepo) {
        this.recenzeRepo = recenzeRepo;
        this.buRepo = buRepo;
        this.albumRepo = albumRepo;
        this.skladbaRepo = skladbaRepo;
        this.umelecRepo = umelecRepo;
    }

    public RecenzeEntity create(RecenzeEntity recenze){
        return recenzeRepo.save(recenze);
    }

    public RecenzeEntity delete(RecenzeEntity recenze){
        return recenzeRepo.deleteRecenzeEntityByRecenzeId(recenze.getRecenzeId());
    }

    public RecenzeEntity createNewRecenze(RecenzeDotIn recenzeDot, String typRecenze, Integer buId){
        RecenzeEntity recenze = new RecenzeEntity();
        recenze.setNadpis(recenzeDot.getNadpis());
        recenze.setKomentar(recenzeDot.getKomentar());
        // recenze.setPocethvezd();
        //TODO Optional for number of stars when giving review to an entity
        recenze.setBeznyuzivatelId(buId);
        if("isSkladba".equals(typRecenze)){
            //recenze.setSkladbaId();
            recenze.setAlbumId(0);
            recenze.setUmelecId(0);
            recenze.setKapelaId(0);
        }
        if("isAlbum".equals(typRecenze)){
            recenze.setSkladbaId(0);
            //recenze.setAlbumId();
            recenze.setUmelecId(0);
            recenze.setKapelaId(0);
        }
        if("isUmelec".equals(typRecenze)){
            recenze.setSkladbaId(0);
            recenze.setAlbumId(0);
            //recenze.setUmelecId();
            recenze.setKapelaId(0);
        }
        if("isKapela".equals(typRecenze)){
            recenze.setSkladbaId(0);
            recenze.setAlbumId(0);
            recenze.setUmelecId(0);
            //recenze.setKapelaId();
        }

        return recenzeRepo.save(recenze);
    }
}
