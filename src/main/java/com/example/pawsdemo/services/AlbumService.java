package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UmelecRepository;
import com.example.pawsdemo.utils.B2Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;

@Service
public class AlbumService {


    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    @Autowired
    private AlbumRepository albumRepo;

    @Autowired
    private B2Services b2Services;
    @Autowired
    private UmelecRepository umelecRepo;

    @Autowired
    private SkladbaRepository skladbaRepo;

    //TODO: private RecenzeRepository recenzeRepo; -- When we gonna implemenet it

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    public AlbumEntity create(AlbumEntity album){
        return albumRepo.save(album);
    }

    public AlbumEntity addNewAlbum(final AlbumDtoIn album, MultipartFile coverImage, Integer umelecId) {

        logger.info("entering album zone");
        String coverImageFileName = coverImage.getOriginalFilename();

        final AlbumEntity newAlbum = new AlbumEntity();
        //junk parameter that we'll rewrite with the actual file path, when we know the album's ID
        newAlbum.setCoverImage("ta");
        newAlbum.setPocetskladeb(0);
        newAlbum.setNazev(album.getNazev());
        newAlbum.setPopis(album.getPopis());
        newAlbum.setPublikovano((byte) 1); //TODO: Před odevzdáním implementovat změnu v updatu
        newAlbum.setDelka(Time.valueOf("00:00:00")); //TODO: Při vytvoření není sice potřeba počítat, jak je album dlouhé ale implementuji později
        albumRepo.save(newAlbum);
        newAlbum.setCoverImage(fileUrl + "albumCover/" + newAlbum.getAlbumId() + "/" +  coverImageFileName);
        logger.info("New album has been created");
        UmelecEntity umelec = umelecRepo.findById(umelecId).orElseThrow(() -> new RuntimeException("Artist not found"));
        newAlbum.getUmelci().add(umelec); // Associate the artist with the album
        try {
            b2Services.uploadToB2("albumCover/" + newAlbum.getAlbumId() + "/" + coverImageFileName, coverImage.getBytes(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return albumRepo.save(newAlbum);
    }

    public AlbumEntity update(AlbumEntity album){
        return null; // will add when I finish album creator
    }
}
