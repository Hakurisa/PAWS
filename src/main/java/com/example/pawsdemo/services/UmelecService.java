package com.example.pawsdemo.services;

import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UmelecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmelecService {

    @Autowired
    private UmelecRepository umelecRepo;

    @Autowired
    private SkladbaRepository skladbaRepo;

    @Autowired
    private AlbumRepository albumRepo;

    public UmelecEntity create(UmelecEntity umelec){
        return umelecRepo.save(umelec);
    }

    @Transactional
    public List<AlbumEntity> getUmelecAlbums(int umelecId) {
        UmelecEntity umelec = umelecRepo.findById(umelecId).orElse(null);

        // Check if the UmelecEntity exists
        if (umelec != null) {
            // If the UmelecEntity exists, return the list of albums associated with it
            return umelec.getAlbums().stream().collect(Collectors.toList());
        } else {
            // If the UmelecEntity does not exist (for example, if the ID is invalid), return an empty list
            return List.of();
        }
    }

}
