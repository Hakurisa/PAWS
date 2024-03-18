package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public class UzivatelDtoIn {

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
