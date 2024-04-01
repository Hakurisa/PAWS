package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.backblaze.b2.client.structures.B2UploadFileRequest.builder;

@Service
public class SkladbaService {

    @Autowired
    B2StorageClient storageClient;

    @Value("${backblaze.b2.bucketId}")
    private String bucketId;

    public void saveSong(SkladbaDtoIn skladba, MultipartFile song, MultipartFile coverImage) {
        //TODO - save on DB
        try {
            String songFileName = song.getOriginalFilename();
            String coverImageFileName = coverImage.getOriginalFilename();

            uploadToB2(songFileName, song.getBytes());
            uploadToB2(coverImageFileName, coverImage.getBytes());
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
}
