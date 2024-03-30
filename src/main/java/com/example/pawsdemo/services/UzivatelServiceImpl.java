package com.example.pawsdemo.services;

import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.springframework.stereotype.Service;

@Service("UzivatelService")
public class UzivatelServiceImpl {

    private UzivatelRepository uzivatelRepo;

    public UzivatelEntity create(UzivatelEntity uzivatelEntity){
        return uzivatelRepo.save(uzivatelEntity);
    }

}
