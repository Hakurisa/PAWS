package com.example.pawsdemo.services;

import com.backblaze.b2.client.B2StorageClient;
import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.UmelecDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.exceptions.UserAlreadyExistsException;
import com.example.pawsdemo.models.*;
import com.example.pawsdemo.repository.*;
import com.example.pawsdemo.utils.B2Services;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UzivatelService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    B2StorageClient storageClient;

    @Autowired
    private B2Services b2Services;

    @Value("${backblaze.b2.fileUrl}")
    private String fileUrl;

    @Autowired
    private UzivatelRepository uzivatelRepo;

    @Autowired
    private BURepository buRepo;

    @Autowired
    private UmelecRepository umelecRepo;

    @Autowired
    private AdresaRepository adresaRepo;

    @Autowired
    private AlbumRepository albumRepo;

    private static final Logger logger = LoggerFactory.getLogger(UzivatelService.class);

    private UzivatelService uzivatelService;

    public UzivatelEntity create(UzivatelEntity uzivatel) {
        return uzivatelRepo.save(uzivatel);
    }

    public BeznyuzivatelEntity create(BeznyuzivatelEntity bu){
        return buRepo.save(bu);
    }

    public UmelecEntity create(UmelecEntity umelec){
        return umelecRepo.save(umelec);
    }
    public UzivatelEntity update(UzivatelEntity uzivatel){
        return uzivatelRepo.save(uzivatel);
    }

    public BeznyuzivatelEntity updateBU(BeznyuzivatelEntity beznyuzivatel) {
        return buRepo.save(beznyuzivatel);
    }
    public UmelecEntity updateUmelec(UmelecEntity umelec){return umelecRepo.save(umelec);}

    @Transactional
    public List<PlaylistEntity> getAllPlaylistsByBuId(Integer playlistId){
        BeznyuzivatelEntity beznyuzivatel = buRepo.findById(playlistId).orElseThrow(() -> new RuntimeException("Chyba při načítání"));
        return beznyuzivatel.getPlaylists().stream().collect(Collectors.toList());
    }

    @Transactional
    public Integer getUmelecByAlbumId(Integer albumId){
        AlbumEntity album = albumRepo.findAlbumEntityByAlbumId(albumId);
        String foundId = album.getUmelci().stream().map(UmelecEntity::getUmelecId).map(Objects::toString).collect(Collectors.joining());
        if (!foundId.isEmpty()) {
            return Integer.parseInt(foundId);
        } else {
            return 0;
        }
    }

    public UzivatelEntity registerNewUserAccount(final UzivatelDtoIn userDto, String typUctu) {
        logger.info("in register");
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Účet s emailem " + userDto.getEmail() + " již existuje.");
        }
        final UzivatelEntity user = new UzivatelEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setDatumzalozeni(userDto.getDatumzalozeni());
        user.setDatumnarozeni(userDto.getDatumnarozeni());
        if("isBU".equals(typUctu)){
            user.setBeznyuzivatelId(uzivatelRepo.getBUIdOfNewUzivatel());
            user.setUmelecId(null);
        }
        if("isUmelec".equals(typUctu)){
            user.setBeznyuzivatelId(null);
            user.setUmelecId(uzivatelRepo.getUmelecIdOfNewUzivatel());
        }
        //FIXME: hack, change this for when we actually have a default icon and ability to change them
        user.setProfilovyobrazek(fileUrl + "default/default-user-icon.png");
        user.setPlatnost((byte) 1);
        user.setAdresaId(uzivatelRepo.getAdresaOfUzivatel());
        return uzivatelRepo.save(user);
    }

    public AdresaEntity registerUsersAddress(final AdresaDtoIn adresaDto) {
        final AdresaEntity adresa = new AdresaEntity();
        adresa.setCislopopisne(adresaDto.getCislopopisne());
        adresa.setMesto(adresaDto.getMesto());
        adresa.setPsc(adresaDto.getPsc());
        adresa.setUlice(adresaDto.getUlice());
        return adresaRepo.save(adresa);
    }

    public BeznyuzivatelEntity registerNewUserAccountAsBU(final BeznyUzivatelDotIn buDto) {
        final BeznyuzivatelEntity bu = new BeznyuzivatelEntity();
        bu.setJmeno(buDto.getJmeno());
        bu.setPrijmeni(buDto.getPrijmeni());
        bu.setOblibenezanry(null);
        return buRepo.save(bu);
    }

    public UmelecEntity registerNewUserAccountAsUmelec(final UmelecDtoIn umelecDto) {
        UmelecEntity umelec = new UmelecEntity();
        umelec.setJmeno(umelecDto.getJmeno());
        umelec.setPopis("Krátký popis posluchačům o tom kdo jste");
        umelec.setClenkapely(null);
        return umelecRepo.save(umelec);
    }

    public void updateProfile(UzivatelDtoIn uzivatel, Integer currentUserId, BeznyUzivatelDotIn buDto, Integer currentBuId, MultipartFile profilePicture) {
        String pfpFileName = profilePicture.getOriginalFilename();

        UzivatelEntity user = uzivatelRepo.findUzivatelEntityByBeznyuzivatelId(currentBuId);
        BeznyuzivatelEntity bu = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(currentBuId);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + currentUserId);
        }

        bu.setJmeno(buDto.getJmeno());
        bu.setPrijmeni(buDto.getPrijmeni());
        user.setEmail(uzivatel.getEmail());

        if(!pfpFileName.isBlank()) {
            try {
                user.setProfilovyobrazek(fileUrl + "pfp/" + user.getUzivatelId() + "/" +  pfpFileName);
                b2Services.uploadToB2("pfp/" + user.getUzivatelId() + "/" + pfpFileName, profilePicture.getBytes(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        update(user);
        updateBU(bu);
    }

    public void updateProfile(UzivatelDtoIn uzivatel, Integer currentUserId, UmelecDtoIn umelecDto, Integer currentUmelecId, MultipartFile profilePicture, String description) {
        String pfpFileName = profilePicture.getOriginalFilename();

        UzivatelEntity user = uzivatelRepo.findUzivatelEntityByUmelecId(currentUmelecId);
        UmelecEntity umelec = umelecRepo.findUmelecEntityByUmelecId(currentUmelecId);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + currentUserId);
        }

        umelec.setJmeno(umelecDto.getJmeno());

        if(!pfpFileName.isBlank()) {
            try {
                user.setProfilovyobrazek(fileUrl + "pfp/" + user.getUzivatelId() + "/" +  pfpFileName);
                b2Services.uploadToB2("pfp/" + user.getUzivatelId() + "/" + pfpFileName, profilePicture.getBytes(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!description.isBlank()) {
            umelec.setPopis(description);
        }
        update(user);
        updateUmelec(umelec);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("in user load!");
        final UzivatelEntity uzivatel = uzivatelRepo.findUzivatelByUsername(username);
        if (uzivatel == null) {
            logger.info("User is null!");
            throw new UsernameNotFoundException(username);
        }
        logger.info("User details fetched: {}", uzivatel.getUsername());
        return new User(uzivatel.getUsername(), uzivatel.getPassword(), new ArrayList<>());
    }

    /**
     * Checks if the email already existing in database
     *
     * @param email email we're searching
     * @return true if no account with email exists in DB
     */
    private boolean emailExists(final String email) {
        return uzivatelRepo.findUzivatelByEmail(email) != null;
    }
}