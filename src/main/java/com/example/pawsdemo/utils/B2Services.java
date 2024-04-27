package com.example.pawsdemo.utils;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.backblaze.b2.client.structures.B2UploadFileRequest.builder;

@Component
public class B2Services {
    @Autowired
    B2StorageClient storageClient;

    @Value("${backblaze.b2.bucketId}")
    private String bucketId;


    /**
     * Sends a request to the B2 bucket to upload
     * @param fileName File path which you desire in the bucket
     * @param fileBytes File which you want uploaded, converted to an array of bytes
     * @param isMusic true - contentType will be "audio/mpeg", false - contentType will be "image/jpeg"
     */
    public void uploadToB2(String fileName, byte[] fileBytes, boolean isMusic) {
        try{
            //finding this in the documentation made me very sad
            B2ContentSource contentSource = B2ByteArrayContentSource.build(fileBytes);
            B2UploadFileRequest request;
            if(isMusic) {
                request = builder(
                        bucketId,
                        fileName,
                        "audio/mpeg",
                        contentSource
                ).build();
            } else {
                request = builder(
                        bucketId,
                        fileName,
                        "image/jpeg",
                        contentSource
                ).build();
            }
            storageClient.uploadSmallFile(request);

        } catch (B2Exception e) {
            throw new RuntimeException("Error when saving to B2.");
        }
    }
}
