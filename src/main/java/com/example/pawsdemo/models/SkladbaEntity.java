package com.example.pawsdemo.models;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "Skladba", schema = "PAWS", catalog = "")
public class SkladbaEntity {
    @Basic
    @Column(name = "Audioslozka")
    private String audioslozka;
    @Basic
    @Column(name = "Coverimage")
    private String coverimage;
    @Basic
    @Column(name = "Delka")
    private Time delka;
    @Basic
    @Column(name = "Jmeno")
    private String jmeno;
    @Basic
    @Column(name = "Pocetprehrani")
    private int pocetprehrani;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SkladbaID")
    private int skladbaId;
    @Basic
    @Column(name = "AlbumID")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkladbaEntity that = (SkladbaEntity) o;

        if (pocetprehrani != that.pocetprehrani) return false;
        if (skladbaId != that.skladbaId) return false;
        if (albumId != that.albumId) return false;
        if (audioslozka != null ? !audioslozka.equals(that.audioslozka) : that.audioslozka != null) return false;
        if (coverimage != null ? !coverimage.equals(that.coverimage) : that.coverimage != null) return false;
        if (delka != null ? !delka.equals(that.delka) : that.delka != null) return false;
        if (jmeno != null ? !jmeno.equals(that.jmeno) : that.jmeno != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = audioslozka != null ? audioslozka.hashCode() : 0;
        result = 31 * result + (coverimage != null ? coverimage.hashCode() : 0);
        result = 31 * result + (delka != null ? delka.hashCode() : 0);
        result = 31 * result + (jmeno != null ? jmeno.hashCode() : 0);
        result = 31 * result + pocetprehrani;
        result = 31 * result + skladbaId;
        result = 31 * result + albumId;
        return result;
    }
}