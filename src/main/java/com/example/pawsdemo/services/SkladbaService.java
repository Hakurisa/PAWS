package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.models.ZanrEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.ZanrRepository;
import com.example.pawsdemo.utils.B2Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

@Service
public class SkladbaService {

    @Autowired
    B2StorageClient storageClient;

    @Autowired
    B2Services b2Services;


    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    @Autowired
    private SkladbaRepository skladbaRepo;
    @Autowired
    private AlbumRepository albumRepo;

    private static final Logger logger = LoggerFactory.getLogger(SkladbaService.class);

    @Autowired
    private ZanrRepository zanrRepository;

    public List<SkladbaEntity> getAllSkladbyByAlbumId(Integer id){
        return skladbaRepo.getSkladbaEntitiesByAlbumId(id);
    }

    public void saveSong(SkladbaDtoIn skladba, MultipartFile song, Integer albumId, Integer zanrId) {
        try {
            String songFileName = song.getOriginalFilename();

            int durationInSeconds = getSongDurationInSeconds(song); //result - 158
            logger.info("Duration in seconds: " + durationInSeconds);
            LocalTime songLength = LocalTime.of(0,0,0).plusSeconds(durationInSeconds);

            int seconds = durationInSeconds % 60;
            int minutes = (durationInSeconds / 60) % 60;
            int hours = durationInSeconds / 3600;

            logger.info("Song length - time format: " + songLength);

            //saving the song into database
            final SkladbaEntity skladbaEntity = new SkladbaEntity();
            skladbaEntity.setAudioslozka("ta");
            skladbaEntity.setCoverimage("ta");
            skladbaEntity.setJmeno(skladba.getJmeno());
            skladbaEntity.setDelka(songLength);
            skladbaEntity.setPocetprehrani(0);


            Optional<AlbumEntity> albumOptional = albumRepo.findById(albumId);

            if(albumOptional.isPresent()) {
                AlbumEntity album = albumOptional.get();

                skladbaEntity.setAlbumId(album.getAlbumId());
                skladbaEntity.setCoverimage(album.getCoverImage());
                int currentPocetSkladeb = album.getPocetskladeb();

//                long currentAlbumLength = album.getDelka().getTime();
//                logger.info("Current album length: " + currentAlbumLength);
//                logger.info("Duration of the song (in seconds): " + durationInSeconds);
//                long sumMilis = currentAlbumLength + (durationInSeconds * 1000);
//                int sumSeconds = (int) (sumMilis/1000) % 60;
//                int sumMinutes = (int) ((sumMilis / (1000 * 60)) % 60);;
//                int sumHours = (int) ((sumMilis / (1000 * 60 * 60)) % 24);
//                Time newTime = new Time(sumHours, sumMinutes, sumSeconds);

                LocalTime currentAlbumLength = album.getDelka();
                LocalTime newTime = currentAlbumLength.plusSeconds(durationInSeconds);
                album.setDelka(newTime);


                album.setPocetskladeb(++currentPocetSkladeb);
                albumRepo.save(album);

            } else {
                throw new RuntimeException("Album with ID " + albumId + " not found.");
            }

            skladbaRepo.save(skladbaEntity);
            int generatedSkladbaId = skladbaEntity.getSkladbaId();

            skladbaEntity.setAudioslozka(fileUrl + "song/" + generatedSkladbaId + "/" + songFileName);

            ZanrEntity zanr = zanrRepository.findById(zanrId).orElseThrow(() -> new RuntimeException("Genre not found"));
            skladbaEntity.getZanry().add(zanr);

            skladbaRepo.save(skladbaEntity);
            //upload to B2 with a file structure
            b2Services.uploadToB2("song/" + skladbaEntity.getSkladbaId() + "/" + songFileName, song.getBytes(), true);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file.");
        }
    }

    public SkladbaEntity updateSkladba(SkladbaDtoIn skladba, Integer id) {
        logger.info("In service update");
        SkladbaEntity existingSkladba = getSkladbaById(id);
        existingSkladba.setJmeno(skladba.getJmeno());
        //TODO: changing albums but that's a problem for later me :)
        return skladbaRepo.save(existingSkladba);
    }

    public void deleteSkladba(int id) {
        SkladbaEntity skladba = skladbaRepo.findById(id).orElseThrow(() -> new RuntimeException("Skladba not found"));

        skladbaRepo.delete(skladba);
    }

    public void incrementPlayCount(int id) {
        SkladbaEntity skladba = skladbaRepo.findById(id).orElseThrow(() -> new RuntimeException("Skladba not found"));
        int current = skladba.getPocetprehrani();
        skladba.setPocetprehrani(++current);
        logger.info("incrementing");
        skladbaRepo.save(skladba);
    }

    /**
     * Takes a multipart file, assumes it's a song and returns its length in seconds
     * @param song The song file - MultipartFile type
     * @return int type - seconds
     */
    private int getSongDurationInSeconds(MultipartFile song) {
        try {
            AudioFile audioFile = AudioFileIO.read(convertMultipartFileToFile(song));
            AudioHeader audioHeader = audioFile.getAudioHeader();
            removeFileFromDirectory(song);
            return audioHeader.getTrackLength();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting song duration." + e);
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        try {
            // Create a new File
            File file = new File("./target/" + multipartFile.getOriginalFilename());

            // Create a FileOutputStream for the file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                // Copy the contents of the MultipartFile to the FileOutputStream
                FileCopyUtils.copy(multipartFile.getInputStream(), fos);
            }

            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error converting MultipartFile to File: " + e.getMessage(), e);
        }
    }

    private boolean removeFileFromDirectory(MultipartFile multipartFile) {
        File file = new File("./target/" + multipartFile.getOriginalFilename());
        return file.delete();
    }

    public SkladbaDtoIn getSkladbaDtoById(int skladbaId) {
        SkladbaEntity skladba = skladbaRepo.findById(skladbaId).orElseThrow(() -> new RuntimeException("Skladba not found"));
        SkladbaDtoIn skladbaDtoIn = new SkladbaDtoIn();
        skladbaDtoIn.setSkladbaId(skladba.getSkladbaId());
        skladbaDtoIn.setAlbumId(skladba.getAlbumId());
        skladbaDtoIn.setJmeno(skladba.getJmeno());
        return skladbaDtoIn;
    }

    public SkladbaEntity getSkladbaById(int skladbaId) {
        return skladbaRepo.findById(skladbaId).orElseThrow(() -> new RuntimeException("Skladba not found"));
    }

    public AlbumEntity getAlbumBySkladbaId(int skladbaId) {
        return skladbaRepo.findAlbumEntityBySkladbaId(skladbaId);
    }
}
