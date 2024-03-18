package com.example.pawsdemo.services;

import com.example.pawsdemo.models.Skladba;
import com.example.pawsdemo.models.Uzivatel;
import com.example.pawsdemo.repository.UzivatelRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
;import java.util.List;

public class UzivatelService implements UserDetailsService {

    private UzivatelRepository uzivatelRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public Uzivatel create(Uzivatel uzivatel){
        return null;
    }

}