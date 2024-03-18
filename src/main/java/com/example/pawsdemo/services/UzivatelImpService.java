package com.example.pawsdemo.services;

import com.example.pawsdemo.models.Uzivatel;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service("uzivatelService")
// @Transactional
public class UzivatelImpService /* implements UzivatelService */{

    //@Autowired
    private UzivatelRepository uzivatelRepo;

   /* @Override
    public Uzivatel vytvořit(Uzivatel uzivatel) {
        return uzivatelRepo.save(uzivatel);
    } */
}
