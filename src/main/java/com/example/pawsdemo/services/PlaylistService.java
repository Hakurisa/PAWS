package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyUzivatelPlaylistEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.repository.BUPlaylistRepository;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

@Service
public class PlaylistService {

    @Autowired
    B2StorageClient storageClient;

    @Value("${backblaze.b2.bucketId}")
    private String bucketId;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    private BURepository buRepo;

    private PlaylistRepository playlistRepo;

    private BUPlaylistRepository buPlaylistRepo;

    public PlaylistEntity create(PlaylistEntity playlist){
        return playlistRepo.save(playlist);
    }

    public BeznyUzivatelPlaylistEntity create(BeznyUzivatelPlaylistEntity buConnectionToPlaylist){
        return buPlaylistRepo.save(buConnectionToPlaylist);
    }

    public PlaylistEntity newPlaylist(PlaylistDtoIn playlistDto, MultipartFile coverImage, String jmenoBU) {
        String coverImageFileName = coverImage.getOriginalFilename();

        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setNazev(playlistDto.getNazev());
        playlist.setPopis(playlistDto.getPopis());
        playlist.setDelka(Time.valueOf("00:00:00"));
        playlist.setTvurce(jmenoBU);
        playlist.setPocetskladeb(0);
        /* if(playlistDto.getCoverImage().isEmpty()){
            playlist.getCoverimage("emply - insert link");
        } else { */
            playlist.setCoverimage(fileUrl + "/playlistCover" + playlist.getPlaylistId() + "/" + coverImageFileName);
        //}
        playlist.setDatumvzniku(playlistDto.getDatumVzniku());
        return playlistRepo.save(playlist);
    }

    public BeznyUzivatelPlaylistEntity newBUToPlaylistConnection(Integer buId){
        BeznyUzivatelPlaylistEntity buPlaylist = new BeznyUzivatelPlaylistEntity();
        buPlaylist.setBeznyuzivatelId(buId);
        buPlaylist.setPlaylistId(playlistRepo.getNewPlaylistId());
        return buPlaylistRepo.save(buPlaylist);
    }
}
