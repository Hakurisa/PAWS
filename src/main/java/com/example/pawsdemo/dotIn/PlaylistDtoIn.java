package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDate;

public class PlaylistDtoIn {

    @NotNull
    private String coverImage;

    @NotNull
    private LocalDate datumVzniku;

    @NotNull
    private Time delka;

    @NotNull
    private String nazev;

    private String popis;

    @NotNull
    private int pocetSkladeb;

    @NotNull
    private int tvurce; //id bezneho uzivatele

    @Id
    @NotNull
    private int playlistId;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public LocalDate getDatumVzniku() {
        return datumVzniku;
    }

    public void setDatumVzniku(LocalDate datumVzniku) {
        this.datumVzniku = datumVzniku;
    }

    public Time getDelka() {
        return delka;
    }

    public void setDelka(Time delka) {
        this.delka = delka;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public int getPocetSkladeb() {
        return pocetSkladeb;
    }

    public void setPocetSkladeb(int pocetSkladeb) {
        this.pocetSkladeb = pocetSkladeb;
    }

    public int getTvurce() {
        return tvurce;
    }

    public void setTvurce(int tvurce) {
        this.tvurce = tvurce;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
}