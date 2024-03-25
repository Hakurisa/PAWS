package com.example.pawsdemo.dotIn;

import com.example.pawsdemo.validation.ValidEmail;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

public class UzivatelDtoIn {


    //there is no reason to make a constructor here!
    /*public UzivatelDtoIn(int uzivatelID, Date datumnarozeni, Date datumzalozeni, String username, String password) {
        this.uzivatelID = uzivatelID;
        this.datumnarozeni = datumnarozeni;
        this.datumzalozeni = datumzalozeni;
        this.username = username;
        this.password = password;
    }*/

    @Id
    @NotNull
    private int uzivatelID;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datumnarozeni;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datumzalozeni = LocalDate.now();


    @NotNull
    private String username;

    @ValidEmail
    @NotEmpty
    @NotNull
    private String email;

    @NotNull
    private String password;

    public int getUzivatelID() {
        return uzivatelID;
    }

    public void setUzivatelID(int uzivatelID) {
        this.uzivatelID = uzivatelID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
