package com.example.pawsdemo.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Uzivatel")
public class Uzivatel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "UzivatelID")
    private int uzivatelID;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "Datumnarozeni", nullable = false)
    private java.sql.Date datumnarozeni;

    @Column(name = "Datumzalozeni", nullable = false)
    private java.sql.Date datumzalozeni;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Platnost", nullable = false)
    private boolean platnost;

    @Column(name = "Profilovyobrazek", nullable = false, length = 255)
    private String profilovyobrazek;

    @ManyToOne
    @JoinColumn(name = "AdresaID", nullable = false)
    private Adresa adresa;

    @ManyToOne
    @JoinColumn(name = "BeznyuzivatelID")
    private BeznyUzivatel beznyuzivatel;

    @ManyToOne
    @JoinColumn(name = "KapelaID")
    private Kapela kapela;

    @ManyToOne
    @JoinColumn(name = "UmelecID")
    private Umelec umlec;
}
