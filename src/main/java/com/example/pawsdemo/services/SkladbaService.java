package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.SkladbaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

import static com.backblaze.b2.client.structures.B2UploadFileRequest.builder;

@Service
public class SkladbaService {

    @Autowired
    B2StorageClient storageClient;

    @Value("${backblaze.b2.bucketId}")
    private String bucketId;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    private static final Logger logger = LoggerFactory.getLogger(SkladbaService.class);


    public void saveSong(SkladbaDtoIn skladba, MultipartFile song, MultipartFile coverImage) {
        //TODO - save on DB
        try {
            String songFileName = song.getOriginalFilename();
            String coverImageFileName = coverImage.getOriginalFilename();

            int durationInSeconds = getSongDurationInSeconds(song);
            logger.info("Duration in seconds: " + durationInSeconds);

            //uploading to cloud storage
            uploadToB2(songFileName, song.getBytes());
            uploadToB2(coverImageFileName, coverImage.getBytes());

            //saving the song into database
            final SkladbaEntity skladbaEntity = new SkladbaEntity();
            skladbaEntity.setAudioslozka(fileUrl + songFileName);
            skladbaEntity.setCoverimage(fileUrl + coverImageFileName);
            skladbaEntity.setJmeno(skladba.getJmeno());
            skladbaEntity.setDelka(new Time(durationInSeconds));
            skladbaEntity.setPocetprehrani(0);
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
            AudioFile audioFile = AudioFileIO.read((File) song);
            AudioHeader audioHeader = audioFile.getAudioHeader();
            return audioHeader.getTrackLength();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting song duration.");
        }
    }
}
