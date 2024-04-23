package com.example.pawsdemo.services;


import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyUzivatelPlaylistEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.repository.BUPlaylistRepository;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;

import static com.backblaze.b2.client.structures.B2UploadFileRequest.builder;

//@Service
public class BUService {

    //@Autowired
    B2StorageClient storageClient;
  
    private UzivatelRepository uzivatelRepo;

    private static final Logger logger = LoggerFactory.getLogger(BUService.class);

    @Autowired
    public BUService(UzivatelRepository uzivatelRepo) {
        this.uzivatelRepo = uzivatelRepo;
    }

}
