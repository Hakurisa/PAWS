package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UmelecRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

//@Service
public class AlbumService {

    /* @Value("${backblaze.b2.bucketId}")
    private String bucketId;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    private AlbumRepository albumRepo;

    private UmelecRepository umelecRepo;

    private SkladbaRepository skladbaRepo;

    //TODO: private RecenzeRepository recenzeRepo; -- When we gonna implemenet it

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    private AlbumService albumService;
    public AlbumEntity create(AlbumEntity album){
        return albumRepo.save(album);
    }

    public AlbumEntity addNewAlbum(final AlbumDtoIn album, MultipartFile coverImage) {

        String coverImageFileName = coverImage.getOriginalFilename();

        final AlbumEntity newAlbum = new AlbumEntity();
        newAlbum.setCoverImage(fileUrl + "albumCover/" + album.getAlbumId() + "/" +  coverImageFileName);
        newAlbum.setPocetskladeb(0);
        newAlbum.setNazev(album.getNazev());
        newAlbum.setPopis(album.getPopis());
        newAlbum.setPublikovano((byte) 1); //TODO: Před odevzdáním implementovat změnu v updatu
        newAlbum.setDelka(Time.valueOf("00:00:00")); //TODO: Při vytvoření není sice potřeba počítat, jak je album dlouhé ale implementuji později

        logger.info("New album has been created");

        return albumRepo.save(newAlbum);
    }

    public AlbumEntity update(AlbumEntity album){
        return null; // will add when I finish album creator
    } */
}
