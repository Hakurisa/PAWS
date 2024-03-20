package com.example.pawsdemo.services;

import com.example.pawsdemo.models.Uzivatel;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
@Configuration("com.example.pawsdemo.repository")
public class UzivatelService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UzivatelRepository uzivatelRepo;

    public Uzivatel create(Uzivatel uzivatel){
        return  uzivatelRepo.save(uzivatel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Uzivatel uzivatel = uzivatelRepo.findUzivatelByUsername(username);
        if(uzivatel == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(uzivatel.getUsername(), passwordEncoder.encode(uzivatel.getPassword()), new ArrayList<>());
    }
}