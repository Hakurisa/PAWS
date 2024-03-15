package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumID")
    private int albumID;

    @Column(name = "Delka", nullable = false)
    private java.sql.Time delka;

    @Column(name = "Nazev", nullable = false, length = 50)
    private String nazev;

    @Column(name = "Pocetskladeb", nullable = false)
    private int pocetskladeb;

    @Column(name = "Popis", columnDefinition = "TEXT")
    private String popis;

    @Column(name = "Publikovano", nullable = false)
    private boolean publikovano;

    @Column(name = "CoverImage", length = 255)
    private String coverImage;
}
