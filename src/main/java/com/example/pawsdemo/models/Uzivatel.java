package com.example.pawsdemo.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Uzivatel")
public class Uzivatel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UzivatelID")
    private int uzivatelID;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "Datumnarozeni", nullable = false)
    private java.sql.Date datumnarozeni;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "Datumzalozeni", nullable = false)
    private java.sql.Date datumzalozeni;

    @Column(name = "Uzivatelskejmeno", nullable = false)
    private String username;

    @Column(name = "Heslo", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "uzivatel_role_junction",
            joinColumns = {@JoinColumn(name="uzivatelID")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")}
    )
    private Set<Role> authorities;

    public Uzivatel(){
        super();
        this.authorities = new HashSet<Role>();
    }

    public Uzivatel(int uzivatelID, Date datumnarozeni, Date datumzalozeni, String username, String password, Set<Role> authorities, String email) {
        this.uzivatelID = uzivatelID;
        this.datumnarozeni = datumnarozeni;
        this.datumzalozeni = datumzalozeni;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
    }



    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
