package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.repository.AlbumRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
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
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.TimeZone;

import static com.backblaze.b2.client.structures.B2UploadFileRequest.builder;

@Service
public class SkladbaService {

    @Autowired
    B2StorageClient storageClient;

    @Value("${backblaze.b2.bucketId}")
    private String bucketId;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    @Autowired
    private SkladbaRepository skladbaRepo;
    @Autowired
    private AlbumRepository albumRepo;

    private static final Logger logger = LoggerFactory.getLogger(SkladbaService.class);


    public void saveSong(SkladbaDtoIn skladba, MultipartFile song, MultipartFile coverImage) {
        //TODO - save on DB
        try {
            String songFileName = song.getOriginalFilename();
            String coverImageFileName = coverImage.getOriginalFilename();

            int durationInSeconds = getSongDurationInSeconds(song); //result - 158
            logger.info("Duration in seconds: " + durationInSeconds);
            long milliseconds = durationInSeconds * 1000L;

            int seconds = durationInSeconds % 60;
            int minutes = (durationInSeconds / 60) % 60;
            int hours = durationInSeconds / 3600;

            Date date = new Date(milliseconds);
            Time songLength = new Time(hours, minutes, seconds);
            logger.info("Song length - time format: " + songLength);

            //saving the song into database
            final SkladbaEntity skladbaEntity = new SkladbaEntity();
            final AlbumEntity album = new AlbumEntity();
            //FIXME: doesn't actually set the song's ID in the database because the ID is still zero at this point
            skladbaEntity.setAudioslozka(fileUrl + "song/" + skladbaEntity.getSkladbaId() + "/" + songFileName);
            skladbaEntity.setCoverimage(fileUrl + "songCover/" + skladbaEntity.getSkladbaId() + "/" + coverImageFileName);
            skladbaEntity.setJmeno(skladba.getJmeno());
            skladbaEntity.setDelka(songLength);
            skladbaEntity.setPocetprehrani(0);

            //FIXME: hack - there's no implementation of an album yet, so I make one up
            album.setCoverImage(fileUrl + "songCover/" + skladbaEntity.getSkladbaId() + "/" +  coverImageFileName);
            album.setPocetskladeb(1);
            album.setNazev(skladba.getJmeno());
            album.setPublikovano((byte) 1);
            album.setPopis("Popis...");
            album.setDelka(songLength);

            albumRepo.save(album);
            skladbaEntity.setAlbumId(album.getAlbumId());
            skladbaRepo.save(skladbaEntity);

            //upload to B2 with a file structure
            uploadToB2("song/" + skladbaEntity.getSkladbaId() + "/" + songFileName, song.getBytes());
            uploadToB2("songCover/" + skladbaEntity.getSkladbaId() + "/" + coverImageFileName, coverImage.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file.");
        }
    }

    private void uploadToB2(String fileName, byte[] fileBytes) {
        try{
            //finding this in the documentation made me very sad
            B2ContentSource contentSource = B2ByteArrayContentSource.build(fileBytes);
            B2UploadFileRequest request = builder(
                    bucketId,
                    fileName,
                    "application/octet-stream",
                    contentSource
            ).build();
            storageClient.uploadSmallFile(request);
        } catch (B2Exception e) {
            throw new RuntimeException("Error when saving to B2.");
        }
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
}
