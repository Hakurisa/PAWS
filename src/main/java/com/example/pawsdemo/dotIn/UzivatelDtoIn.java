package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;

public class UzivatelDtoIn {

    public UzivatelDtoIn(int uzivatelID, Date datumnarozeni, Date datumzalozeni, String username, String password) {
        this.uzivatelID = uzivatelID;
        this.datumnarozeni = datumnarozeni;
        this.datumzalozeni = datumzalozeni;
        this.username = username;
        this.password = password;
    }

    @Id
    @NotBlank
    private int uzivatelID;

    @NotBlank
    private java.sql.Date datumnarozeni;

    @NotBlank
    private java.sql.Date datumzalozeni;


    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
