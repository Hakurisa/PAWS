package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;

import java.sql.Time;
import java.time.LocalTime;

public class AlbumDtoIn {

    @NotNull
    private LocalTime delka;
    @NotNull
    private String nazev;
    private String popis;
    @NotNull
    private int pocetSkladeb;
    @NotNull
    private byte publikovano;
    private String coverImage;
    @Id
    @NotNull
    private int albumId;

    public LocalTime getDelka() {
        return delka;
    }

    public void setDelka(LocalTime delka) {
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

    public byte getPublikovano() {
        return publikovano;
    }

    public void setPublikovano(byte publikovano) {
        this.publikovano = publikovano;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
