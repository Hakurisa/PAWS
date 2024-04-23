package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.utils.B2Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.util.TimeZone;

@Service
public class PlaylistService {

    @Autowired
    B2StorageClient storageClient;

    @Autowired
    private B2Services b2Services;

    @Value("${backblaze.b2.bucketId}")
    private String bucketId;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    private BURepository buRepo;

    @Autowired
    private PlaylistRepository playlistRepo;

    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    public PlaylistEntity create(PlaylistEntity playlist){
        return playlistRepo.save(playlist);
    }

    public PlaylistEntity newPlaylist(PlaylistDtoIn playlistDto, MultipartFile coverImage, String jmenoBU, Integer buId) {
        String coverImageFileName = coverImage.getOriginalFilename();

        final PlaylistEntity playlist = new PlaylistEntity();
        playlist.setNazev(playlistDto.getNazev());
        playlist.setPopis(playlistDto.getPopis());
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        playlist.setDatumvzniku(playlistDto.getDatumVzniku());
        playlist.setDelka(new Time(0,0,0));
        playlist.setTvurce(jmenoBU);
        playlist.setPocetskladeb(0);
        if(coverImageFileName.isBlank()){
            playlist.setCoverimage(fileUrl + "default/playlistPlaceholder.png");
        } else {
            playlist.setCoverimage(fileUrl + "/playlistCover" + playlist.getPlaylistId() + "/" + coverImageFileName);
        }
        if(!coverImageFileName.isBlank()) {
            try {
                b2Services.uploadToB2("playlistCover/" + playlist.getPlaylistId() + "/" + coverImageFileName, coverImage.getBytes(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("we ain't uploadin' shit");
        }
        BeznyuzivatelEntity beznyuzivatel = buRepo.findById(buId).orElseThrow(() -> new RuntimeException("Artist not found"));
        playlist.getUzivatele().add(beznyuzivatel);
        return playlistRepo.save(playlist);
    }
}
