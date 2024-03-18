package com.example.pawsdemo.services;

import com.example.pawsdemo.exceptions.SkladbaNotFoundException;
import com.example.pawsdemo.models.Skladba;
import com.example.pawsdemo.repository.SkladbaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// @Service // - prozatím není funkční. Funkce ještě nejsou zavedeny do provozu
// @Transactional
public class SkladbaService {
    // @Autowired
    public SkladbaRepository skladbaRepo;

    public List<Skladba> getSkladby() {
        return (List<Skladba>) skladbaRepo.findAll();
    }

    public Skladba pridatSkladbu(Skladba skladba) {
        return skladbaRepo.save(skladba);
    }

    public void odstranitSkladbu(Skladba s) {
        skladbaRepo.delete(s);
    }

    public void odstranitSkladbu(int skladbaId) throws SkladbaNotFoundException {
        Skladba s = getSkladba(skladbaId);
        skladbaRepo.delete(s);
    }

    public Skladba getSkladba(int skladbaId) throws SkladbaNotFoundException {
        Optional<Skladba> skladbaOptional = skladbaRepo.findById(skladbaId);
        if(skladbaOptional.isPresent())
            return skladbaOptional.get();
        else
            throw new SkladbaNotFoundException("Skladba, kterou hledáte nebyla nalezena");
    }

    public List<Skladba> findSkladba(String jemno){
        return skladbaRepo.findSkladba(jemno);
    }
}
