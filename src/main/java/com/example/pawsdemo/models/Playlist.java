package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistID")
    private int playlistID;

    @Column(name = "Coverimage", length = 255)
    private String coverimage;

    @Column(name = "Datumvzniku", nullable = false)
    private java.sql.Date datumvzniku;

    @Column(name = "Delka", nullable = false)
    private java.sql.Time delka;

    @Column(name = "Nazev", nullable = false, length = 50)
    private String nazev;

    @Column(name = "Pocetskladeb", nullable = false)
    private int pocetskladeb;

    @Column(name = "Popis", columnDefinition = "TEXT")
    private String popis;

    @Column(name = "Tvurce", nullable = false, length = 255)
    private String tvurce;
}
