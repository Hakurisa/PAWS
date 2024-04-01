package com.example.pawsdemo.config;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Configuration
public class B2Config {
    @Value("${backblaze.b2.applicationKeyId}")
    private String applicationKeyId;

    @Value("${backblaze.b2.applicationKey}")
    private String applicationKey;

    private final String userAgent = "PAWS/Spring-Boot/JavaApp";

    @Bean
    public B2StorageClient b2StorageClient() {
        return B2StorageClientFactory.createDefaultFactory().create(applicationKeyId, applicationKey, userAgent);
    }
}
