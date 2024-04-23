package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.models.RecenzeEntity;
import com.example.pawsdemo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecenzeService {

    @Autowired
    private RecenzeRepository recenzeRepo;
    @Autowired
    private BURepository buRepo;
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private SkladbaRepository skladbaRepo;
    @Autowired
    private UmelecRepository umelecRepo;
    //TODO when I will have Repository Kapela from Hakurisa
    // private KapelaRepository kapelaRepo;

    public RecenzeEntity create(RecenzeEntity recenze){
        return recenzeRepo.save(recenze);
    }

    public RecenzeEntity delete(RecenzeEntity recenze){
        return recenzeRepo.deleteRecenzeEntityByRecenzeId(recenze.getRecenzeId());
    }

    public RecenzeEntity createRecenze(RecenzeDtoIn recenzeDot, String typRecenze, Integer buId, Integer id){
        RecenzeEntity recenze = new RecenzeEntity();
        recenze.setNadpis(recenzeDot.getNadpis());
        recenze.setKomentar(recenzeDot.getKomentar());
        recenze.setPocethvezd(1);
        //TODO Optional for number of stars when giving review to an entity
        recenze.setBeznyuzivatelId(buId);
        if("isSkladba".equals(typRecenze)){
            recenze.setSkladbaId(id);
            recenze.setAlbumId(null);
            recenze.setUmelecId(null);
            recenze.setKapelaId(null);
        }
        if("isAlbum".equals(typRecenze)){
            recenze.setSkladbaId(null);
            recenze.setAlbumId(id);
            recenze.setUmelecId(null);
            recenze.setKapelaId(null);
        }
        if("isUmelec".equals(typRecenze)){
            recenze.setSkladbaId(null);
            recenze.setAlbumId(null);
            recenze.setUmelecId(id);
            recenze.setKapelaId(null);
        }
        if("isKapela".equals(typRecenze)){
            recenze.setSkladbaId(null);
            recenze.setAlbumId(null);
            recenze.setUmelecId(null);
            recenze.setKapelaId(id);
        }

        return recenzeRepo.save(recenze);
    }
}
