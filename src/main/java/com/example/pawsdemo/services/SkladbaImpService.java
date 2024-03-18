package com.example.pawsdemo.services;

import com.example.pawsdemo.models.Skladba;
import com.example.pawsdemo.repository.SkladbaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service("SkladbaService")
//@Transactional
public class SkladbaImpService /* implements SkladbaService */ {

    //@Autowired
    private SkladbaRepository skladbaRepo;

    public  Iterable<Skladba> findAll(){
        return skladbaRepo.findAll();
    }
}
