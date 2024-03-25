package com.example.pawsdemo.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Uzivatel", schema = "PAWS", catalog = "")
public class UzivatelEntity implements UserDetails{
    @Basic
    @Column(name = "Datumnarozeni")
    private LocalDate datumnarozeni;
    @Basic
    @Column(name = "Datumzalozeni")
    private LocalDate datumzalozeni;
    @Basic
    @Column(name = "Email")
    private String email;
    @Basic
    @Column(name = "Platnost")
    private byte platnost;
    @Basic
    @Column(name = "Profilovyobrazek")
    private String profilovyobrazek;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UzivatelID")
    private int uzivatelId;
    @Basic
    @Column(name = "AdresaID")
    private int adresaId;
    @Basic
    @Column(name = "BeznyuzivatelID")
    private Integer beznyuzivatelId;
    @Basic
    @Column(name = "KapelaID")
    private Integer kapelaId;
    @Basic
    @Column(name = "UmelecID")
    private Integer umelecId;
    @Basic
    @Column(name = "Username")
    private String username;
    @Basic
    @Column(name = "Password")
    private String password;
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public UzivatelEntity(){

    }
    public UzivatelEntity(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super();
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public LocalDate getDatumnarozeni() {
        return datumnarozeni;
    }

    public void setDatumnarozeni(LocalDate datumnarozeni) {
        this.datumnarozeni = datumnarozeni;
    }

    public LocalDate getDatumzalozeni() {
        return datumzalozeni;
    }

    public void setDatumzalozeni(LocalDate datumzalozeni) {
        this.datumzalozeni = datumzalozeni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getPlatnost() {
        return platnost;
    }

    public void setPlatnost(byte platnost) {
        this.platnost = platnost;
    }

    public String getProfilovyobrazek() {
        return profilovyobrazek;
    }

    public void setProfilovyobrazek(String profilovyobrazek) {
        this.profilovyobrazek = profilovyobrazek;
    }

    public int getUzivatelId() {
        return uzivatelId;
    }

    public void setUzivatelId(int uzivatelId) {
        this.uzivatelId = uzivatelId;
    }

    public int getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(int adresaId) {
        this.adresaId = adresaId;
    }

    public Integer getBeznyuzivatelId() {
        return beznyuzivatelId;
    }

    public void setBeznyuzivatelId(Integer beznyuzivatelId) {
        this.beznyuzivatelId = beznyuzivatelId;
    }

    public Integer getKapelaId() {
        return kapelaId;
    }

    public void setKapelaId(Integer kapelaId) {
        this.kapelaId = kapelaId;
    }

    public Integer getUmelecId() {
        return umelecId;
    }

    public void setUmelecId(Integer umelecId) {
        this.umelecId = umelecId;
    }

    public String getUsername(){
        return username;
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
        return authorities;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
