package com.example.pawsdemo.models;

import jakarta.persistence.*;

import java.sql.Time;

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

    public int getSkladbaID() {
        return skladbaID;
    }

    public void setSkladbaID(int skladbaID) {
        this.skladbaID = skladbaID;
    }

    public Time getDelka() {
        return delka;
    }

    public void setDelka(Time delka) {
        this.delka = delka;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public int getPocetprehrani() {
        return pocetprehrani;
    }

    public void setPocetprehrani(int pocetprehrani) {
        this.pocetprehrani = pocetprehrani;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
