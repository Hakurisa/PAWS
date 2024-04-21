package com.example.pawsdemo.services;


import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BUService {
    //@Autowired
    private UzivatelRepository uzivatelRepo;

    private static final Logger logger = LoggerFactory.getLogger(BUService.class);

    @Autowired
    public BUService(UzivatelRepository uzivatelRepo) {
        this.uzivatelRepo = uzivatelRepo;
    }
}
