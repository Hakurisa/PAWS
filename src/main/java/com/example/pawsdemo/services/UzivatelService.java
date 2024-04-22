package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.dotIn.UmelecDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.exceptions.UserAlreadyExistsException;
import com.example.pawsdemo.models.AdresaEntity;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.UmelecEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.AdresaRepository;
import com.example.pawsdemo.repository.BURepository;
import com.example.pawsdemo.repository.UmelecRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
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

import java.util.ArrayList;

@Service
public class UzivatelService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

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

    public UzivatelEntity updateProfile(UzivatelDtoIn uzivatel, Integer currentUserId, BeznyUzivatelDotIn bu, Integer currentBuId) {
        UzivatelEntity user = uzivatelRepo.findUzivatelEntityByBeznyuzivatelId(currentUserId);
        BeznyuzivatelEntity buEntity = buRepo.findBeznyuzivatelEntityByBeznyuzivatelId(currentBuId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + currentUserId);
        }
        buEntity.setJmeno(bu.getJmeno());
        buEntity.setPrijmeni(bu.getPrijmeni());
        user.setEmail(uzivatel.getEmail());
        buRepo.save(buEntity);
        return uzivatelRepo.save(user);
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