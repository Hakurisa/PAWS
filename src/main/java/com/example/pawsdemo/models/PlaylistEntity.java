package com.example.pawsdemo.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "Playlist", schema = "PAWS", catalog = "")
public class PlaylistEntity {
    @Basic
    @Column(name = "Coverimage")
    private String coverimage;
    @Basic
    @CreationTimestamp
    @Column(name = "Datumvzniku")
    private Date datumvzniku;
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
    @Column(name = "Tvurce")
    private String tvurce;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PlaylistID")
    private int playlistId;

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public Date getDatumvzniku() {
        return datumvzniku;
    }

    public void setDatumvzniku(Date datumvzniku) {
        this.datumvzniku = datumvzniku;
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

    public String getTvurce() {
        return tvurce;
    }

    public void setTvurce(String tvurce) {
        this.tvurce = tvurce;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaylistEntity that = (PlaylistEntity) o;

        if (pocetskladeb != that.pocetskladeb) return false;
        if (playlistId != that.playlistId) return false;
        if (coverimage != null ? !coverimage.equals(that.coverimage) : that.coverimage != null) return false;
        if (datumvzniku != null ? !datumvzniku.equals(that.datumvzniku) : that.datumvzniku != null) return false;
        if (delka != null ? !delka.equals(that.delka) : that.delka != null) return false;
        if (nazev != null ? !nazev.equals(that.nazev) : that.nazev != null) return false;
        if (popis != null ? !popis.equals(that.popis) : that.popis != null) return false;
        if (tvurce != null ? !tvurce.equals(that.tvurce) : that.tvurce != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = coverimage != null ? coverimage.hashCode() : 0;
        result = 31 * result + (datumvzniku != null ? datumvzniku.hashCode() : 0);
        result = 31 * result + (delka != null ? delka.hashCode() : 0);
        result = 31 * result + (nazev != null ? nazev.hashCode() : 0);
        result = 31 * result + pocetskladeb;
        result = 31 * result + (popis != null ? popis.hashCode() : 0);
        result = 31 * result + (tvurce != null ? tvurce.hashCode() : 0);
        result = 31 * result + playlistId;
        return result;
    }
}