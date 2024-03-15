package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Skladba")
public class Skladba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SkladbaID")
    private int skladbaID;

    @Column(name = "Audioslozka", nullable = false, length = 255)
    private String audioslozka;

    @Column(name = "Coverimage", nullable = false, length = 255)
    private String coverimage;

    @Column(name = "Delka", nullable = false)
    private java.sql.Time delka;

    @Column(name = "Jmeno", nullable = false, length = 50)
    private String jmeno;

    @Column(name = "Pocetprehrani", nullable = false)
    private int pocetprehrani;

    @ManyToOne
    @JoinColumn(name = "AlbumID", nullable = false)
    private Album album;
}
