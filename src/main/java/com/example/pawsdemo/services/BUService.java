package com.example.pawsdemo.services;


import com.backblaze.b2.client.B2StorageClient;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BUService {

    //@Autowired
    B2StorageClient storageClient;

    @Autowired
    private UzivatelRepository uzivatelRepo;

    private static final Logger logger = LoggerFactory.getLogger(BUService.class);

    public BUService(UzivatelRepository uzivatelRepo) {
        this.uzivatelRepo = uzivatelRepo;
    }

}
