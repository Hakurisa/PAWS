package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

public class SkladbaDtoIn {

    @NotNull
    private String audioslozka;

    @NotNull
    private String coverimage;

    @NotNull
    private Time delka;

    @NotNull
    private String jmeno;

    @NotNull
    private int pocetprehrani;

    @NotNull
    @Id
    private int skladbaId;

    private int albumId;

    public String getAudioslozka() {
        return audioslozka;
    }

    public void setAudioslozka(String audioslozka) {
        this.audioslozka = audioslozka;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
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

    public int getSkladbaId() {
        return skladbaId;
    }

    public void setSkladbaId(int skladbaId) {
        this.skladbaId = skladbaId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
