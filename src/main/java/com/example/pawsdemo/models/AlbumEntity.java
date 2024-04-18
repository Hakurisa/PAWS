package com.example.pawsdemo.models;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "Album", schema = "PAWS", catalog = "")
public class AlbumEntity {
    @Basic
    @Column(name = "Delka")
    private Time delka;
    @Basic
    @Column(name = "Nazev")
    private String nazev;
    @Basic
    @Column(name = "Pocetskladeb")
    private int pocetskladeb;
    @Basic
    @Column(name = "Popis")
    private String popis;
    @Basic
    @Column(name = "Publikovano")
    private byte publikovano;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AlbumID")
    private int albumId;
    @Basic
    @Column(name = "CoverImage")
    private String coverImage;

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

    public int getPocetskladeb() {
        return pocetskladeb;
    }

    public void setPocetskladeb(int pocetskladeb) {
        this.pocetskladeb = pocetskladeb;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public byte getPublikovano() {
        return publikovano;
    }

    public void setPublikovano(byte publikovano) {
        this.publikovano = publikovano;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumEntity that = (AlbumEntity) o;

        if (pocetskladeb != that.pocetskladeb) return false;
        if (publikovano != that.publikovano) return false;
        if (albumId != that.albumId) return false;
        if (delka != null ? !delka.equals(that.delka) : that.delka != null) return false;
        if (nazev != null ? !nazev.equals(that.nazev) : that.nazev != null) return false;
        if (popis != null ? !popis.equals(that.popis) : that.popis != null) return false;
        if (coverImage != null ? !coverImage.equals(that.coverImage) : that.coverImage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = delka != null ? delka.hashCode() : 0;
        result = 31 * result + (nazev != null ? nazev.hashCode() : 0);
        result = 31 * result + pocetskladeb;
        result = 31 * result + (popis != null ? popis.hashCode() : 0);
        result = 31 * result + (int) publikovano;
        result = 31 * result + albumId;
        result = 31 * result + (coverImage != null ? coverImage.hashCode() : 0);
        return result;
    }
}