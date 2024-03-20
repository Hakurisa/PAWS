package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public class BeznyUzivatelDotIn {
    @Id
    @NotBlank
    private int beznyuzivatelID;
    @NotBlank
    private String jmeno;

    @NotBlank
    private String prijmeni;
    private Integer oblibeneZanry;
}
