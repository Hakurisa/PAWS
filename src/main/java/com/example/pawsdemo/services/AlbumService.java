package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.models.*;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.AuARepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UmelecRepository;
import com.example.pawsdemo.utils.B2Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

    @Autowired
    private AuARepository auARepo;

    //TODO: private RecenzeRepository recenzeRepo; -- When we gonna implemenet it

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    public AlbumEntity create(AlbumEntity album){
        return albumRepo.save(album);
    }

    public List<AlbumEntity> getAllAlbums() {
        return albumRepo.findAll();
    }

    public List<AlbumEntity> getAllAlbumsByStatus(Byte albumStatus){
        return albumRepo.findAllByPublikovano(albumStatus);
    }

    @Transactional
    public List<AlbumEntity> getAlbumsByUmelecId(Integer umelecId) {
        UmelecEntity umelec = umelecRepo.findUmelecEntityByUmelecId(umelecId);
        if (umelec != null) {
            return umelec.getAlbums().stream().collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    public AlbumEntity addNewAlbum(final AlbumDtoIn album, MultipartFile coverImage, Integer umelecId, String copyright) {

        logger.info("entering album zone");
        String coverImageFileName = coverImage.getOriginalFilename();

        final AlbumEntity newAlbum = new AlbumEntity();
        newAlbum.setCoverImage("ta");  //junk parameter that we'll rewrite with the actual file path, when we know the album's ID
        newAlbum.setPocetskladeb(0);
        newAlbum.setNazev(album.getNazev());
        newAlbum.setPopis(album.getPopis());
        newAlbum.setPublikovano(album.getPublikovano()); //TODO: Před odevzdáním implementovat změnu v updatu
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        newAlbum.setDelka(new Time(0, 0, 0)); //TODO: Při vytvoření není sice potřeba počítat, jak je album dlouhé ale implementuji později
        albumRepo.save(newAlbum);
        if(coverImageFileName.isBlank()) {
            newAlbum.setCoverImage(fileUrl + "default/playlistPlaceholder.png");
        } else {
            newAlbum.setCoverImage(fileUrl + "albumCover/" + newAlbum.getAlbumId() + "/" +  coverImageFileName);
        }
        logger.info("New album has been created");
        logger.info("Délka: " + newAlbum.getDelka().getTime());
        AuAEntity auAEntity = new AuAEntity();
        auAEntity.setAlbumID(newAlbum);
        auAEntity.setCopyright(copyright);
        UmelecEntity umelec = umelecRepo.findById(umelecId).orElseThrow(() -> new RuntimeException("Artist not found"));
        auAEntity.getUmelci().add(umelec);
        auARepo.save(auAEntity);
        newAlbum.getUmelci().add(umelec); // Associate the artist with the album
        if(!coverImageFileName.isBlank()) {
            try {
                b2Services.uploadToB2("albumCover/" + newAlbum.getAlbumId() + "/" + coverImageFileName, coverImage.getBytes(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("we ain't uploadin' shit");
        }


        return albumRepo.save(newAlbum);
    }

    public AlbumEntity updateAlbum(AlbumDtoIn album, MultipartFile coverImage, Integer id) {
        AlbumEntity existingAlbum = getAlbumById(id);
        String coverImageFileName = coverImage.getOriginalFilename();

        existingAlbum.setNazev(album.getNazev());
        existingAlbum.setPopis(album.getPopis());
        existingAlbum.setPublikovano(album.getPublikovano());
        logger.info("Publikováno service:" + album.getPublikovano());
        if(!coverImageFileName.isBlank()) {
            try {
                existingAlbum.setCoverImage(fileUrl + "albumCover/" + existingAlbum.getAlbumId() + "/" +  coverImageFileName);
                b2Services.uploadToB2("albumCover/" + existingAlbum.getAlbumId() + "/" + coverImageFileName, coverImage.getBytes(), false);
                //gotta change all the songs' covers when changing an album too
                List<SkladbaEntity> skladbas = skladbaRepo.findSkladbaEntityByAlbumId(id);
                for (SkladbaEntity skladba : skladbas) {
                    skladba.setCoverimage(fileUrl + "albumCover/" + existingAlbum.getAlbumId() + "/" +  coverImageFileName);
                    skladbaRepo.save(skladba);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return update(existingAlbum);
    }

    public AlbumEntity update(AlbumEntity album){
        return albumRepo.save(album); // will add when I finish album creator
    }

    @Transactional
    public void deleteAlbum(int albumId) {
        // Retrieve the album entity by ID
        AlbumEntity album = albumRepo.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        // Delete all skladba entities associated with the album
        List<SkladbaEntity> skladbas = skladbaRepo.findSkladbaEntityByAlbumId(albumId);
        skladbaRepo.deleteAll(skladbas);

        // Delete the album entity
        albumRepo.delete(album);
    }

    public AlbumDtoIn getAlbumDtoById(int albumId) {
        AlbumEntity album = albumRepo.findById(albumId).orElseThrow(() -> new RuntimeException("Album not found"));
        AlbumDtoIn albumDtoIn = new AlbumDtoIn();
        albumDtoIn.setAlbumId(album.getAlbumId());
        albumDtoIn.setNazev(album.getNazev());
        albumDtoIn.setPopis(album.getPopis());
        albumDtoIn.setCoverImage(album.getCoverImage());
        albumDtoIn.setPublikovano(albumDtoIn.getPublikovano());
        albumDtoIn.setDelka(album.getDelka());
        return albumDtoIn;
    }

    public AlbumEntity getAlbumById(int albumId) {
        return albumRepo.findById(albumId).orElseThrow(() -> new RuntimeException("Album not found"));
    }


    @Transactional
    public String getUmelecName(int albumId) {
        AlbumEntity album = albumRepo.findById(albumId).orElseThrow(() -> new RuntimeException("Album not found"));
        if(album != null) {
            Set<UmelecEntity> umelci = album.getUmelci();
            if (!umelci.isEmpty()) {
                UmelecEntity firstUmelec = umelci.iterator().next();
                return firstUmelec.getJmeno();
            }
        }
        return null;
    }
}
