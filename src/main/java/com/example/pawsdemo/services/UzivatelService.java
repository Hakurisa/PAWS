package com.example.pawsdemo.services;

import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.exceptions.UserAlreadyExistsException;
import com.example.pawsdemo.models.AdresaEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import com.example.pawsdemo.repository.AdresaRepository;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Service
public class UzivatelService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UzivatelRepository uzivatelRepo;

    @Autowired
    private AdresaRepository adresaRepo;

    private static final Logger logger = LoggerFactory.getLogger(UzivatelService.class);

    private UzivatelService uzivatelService;

    public UzivatelEntity create(UzivatelEntity uzivatel){
        return  uzivatelRepo.save(uzivatel);
    }

    public UzivatelEntity registerNewUserAccount(final UzivatelDtoIn userDto) {
        logger.info("in register");
        if(emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Účet s emailem " + userDto.getEmail() + " již existuje.");
        }
        final UzivatelEntity user = new UzivatelEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setDatumzalozeni(userDto.getDatumzalozeni());
        user.setDatumnarozeni(userDto.getDatumnarozeni());
//        user.setBeznyuzivatelId(userDto.getBeznyUzivatelID());
//        user.setUmelecId(userDto.getUmelecID());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("in user load!");
        final UzivatelEntity uzivatel = uzivatelRepo.findUzivatelByUsername(username);
        if(uzivatel == null) {
            logger.info("User is null!");
            throw new UsernameNotFoundException(username);
        }
        logger.info("User details fetched: {}", uzivatel.getUsername());
        return new User(uzivatel.getUsername(), uzivatel.getPassword(), new ArrayList<>());
    }

    /**
     * Checks if the email already existing in database
     * @param email email we're searching
     * @return true if no account with email exists in DB
     */
    private boolean emailExists(final String email) {
        return uzivatelRepo.findUzivatelByEmail(email) != null;
    }
}