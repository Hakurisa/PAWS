package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.PlaylistRepository;
import com.example.pawsdemo.repository.SkladbaRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import com.example.pawsdemo.utils.B2Services;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    B2StorageClient storageClient;

    @Autowired
    private B2Services b2Services;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    @Autowired
    private BURepository buRepo;

    @Autowired
    private PlaylistRepository playlistRepo;

    @Autowired
    private SkladbaRepository skladbaRepo;

    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);
    @Autowired
    private UzivatelRepository uzivatelRepository;

    public PlaylistEntity create(PlaylistEntity playlist){
        return playlistRepo.save(playlist);
    }

    public PlaylistEntity update(PlaylistEntity playlist){
        return playlistRepo.save(playlist);
    }

    public SkladbaEntity updateSkladba(SkladbaEntity skladba){
        return skladbaRepo.save(skladba);
    }

    public List<PlaylistEntity> getAllPlaylists() {
        return playlistRepo.findAll();
    }

    public List<PlaylistEntity> getAllUsersPlaylists(String tvurce) {
        return playlistRepo.findAllByTvurce(tvurce);
    }

    @Transactional
    public List<SkladbaEntity> getAllSkladbyByPlaylistId(Integer playlistId){
        PlaylistEntity playlist = playlistRepo.findById(playlistId).orElseThrow(() -> new RuntimeException("Chyba při načítání"));
        return playlist.getSkladbas().stream().collect(Collectors.toList());
    }

    public PlaylistEntity newPlaylist(PlaylistDtoIn playlistDto, MultipartFile coverimage, String jmenoBU, Integer buId) {
        String coverImageFileName = coverimage.getOriginalFilename();

        final PlaylistEntity playlist = new PlaylistEntity();
        playlist.setNazev(playlistDto.getNazev());
        playlist.setPopis(playlistDto.getPopis());
        playlist.setDatumvzniku(playlistDto.getDatumVzniku());
//        playlist.setDelka(new Time(0,0,0));
        playlist.setDelka(LocalTime.of(0, 0, 0));
        playlist.setTvurce(jmenoBU);
        playlist.setPocetskladeb(0);
        playlist.setCoverimage("placeholder"); // Set placeholder first to have data
        playlistRepo.save(playlist); // Save the playlist with the placeholder first, to let JPA generate the PlaylistID for this PlaylistEntity instance
        if(coverImageFileName.isBlank()){
            playlist.setCoverimage(fileUrl + "default/playlistPlaceholder.png");
        } else {
            //...now, getPlaylistId() will actually return the Id you want, and the saving procedure will be correct
            playlist.setCoverimage(fileUrl + "playlistCover/" + playlist.getPlaylistId() + "/" + coverImageFileName);
        }
        if(!coverImageFileName.isBlank()) {
            try {
                b2Services.uploadToB2("playlistCover/" + playlist.getPlaylistId() + "/" + coverImageFileName, coverimage.getBytes(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("we ain't uploadin' this shitty playlist");
        }
        BeznyuzivatelEntity beznyuzivatel = buRepo.findById(buId).orElseThrow(() -> new RuntimeException("Bežný uživatel nebyl nalezen"));
        playlist.getBeznyuzivatels().add(beznyuzivatel);
        return playlistRepo.save(playlist);
    }

    public PlaylistDtoIn getPlaylistDtoById(int playlistId) {
        PlaylistEntity playlist = playlistRepo.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist nebyl nalezen"));
        PlaylistDtoIn playlistDto = new PlaylistDtoIn();
        playlistDto.setPlaylistId(playlist.getPlaylistId());
        playlistDto.setNazev(playlist.getNazev());
        playlistDto.setPopis(playlist.getPopis());
        playlistDto.setTvurce(playlist.getTvurce());
        playlistDto.setDatumVzniku(playlist.getDatumvzniku());
        playlistDto.setCoverImage(playlist.getCoverimage());
        playlistDto.setPocetSkladeb(playlist.getPocetskladeb());
        return playlistDto;
    }

    public PlaylistEntity getPlaylistById(int playlistId) {
        return playlistRepo.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not found"));
    }

    public PlaylistEntity update(PlaylistDtoIn playlistDto, MultipartFile coverimage, Integer playlistId){
        PlaylistEntity pickedPlaylist = getPlaylistById(playlistId);
        String coverImageFileName = coverimage.getOriginalFilename();

        pickedPlaylist.setNazev(playlistDto.getNazev());
        pickedPlaylist.setPopis(playlistDto.getPopis());
        if(!coverImageFileName.isBlank()) {
            try {
                pickedPlaylist.setCoverimage(fileUrl + "playlistCover/" + pickedPlaylist.getPlaylistId() + "/" +  coverImageFileName);
                b2Services.uploadToB2("playlistCover/" + pickedPlaylist.getPlaylistId() + "/" + coverImageFileName, coverimage.getBytes(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return update(pickedPlaylist);
    }

    @Transactional
    public void deletePlaylist(int playlistId) {
        PlaylistEntity playlist = playlistRepo.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.getSkladbas().clear();

        playlistRepo.delete(playlist);
    }

    @Transactional
    public void addSongtoUsersPlaylist(int playlistId, int songId){
        PlaylistEntity playlist = playlistRepo.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist neexistuje"));
        SkladbaEntity skladba = skladbaRepo.findById(songId).orElseThrow(() -> new RuntimeException("Skladba nebyla nalezena"));
        playlist.getSkladbas().add(skladba);
        LocalTime currentLength = playlist.getDelka();
        long songLength = skladba.getDelka().toSecondOfDay();
        playlist.setDelka(currentLength.plusSeconds(songLength));
        int pocet = playlist.getPocetskladeb();
        playlist.setPocetskladeb(++pocet);
        update(playlist);
        updateSkladba(skladba);
    }

    @Transactional
    public String getCreatorUsername(int playlistId) {
        PlaylistEntity playlist = playlistRepo.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist neexistuje"));
        if(playlist != null) {
            Set<BeznyuzivatelEntity> bus = playlist.getBeznyuzivatels();
            if(bus != null) {
                BeznyuzivatelEntity firstBu = bus.iterator().next();
                UzivatelEntity uzivatel = uzivatelRepository.findUzivatelEntityByBeznyuzivatelId(firstBu.getBeznyuzivatelId());
                return uzivatel.getUsername();
            }
        }
        return null;
    }
}
