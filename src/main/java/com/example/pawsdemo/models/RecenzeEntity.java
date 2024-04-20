package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Recenze", schema = "PAWS", catalog = "")
public class RecenzeEntity {
    @Basic
    @Column(name = "Komentar")
    private String komentar;
    @Basic
    @Column(name = "Nadpis")
    private String nadpis;
    @Basic
    @Column(name = "Pocethvezd")
    private int pocethvezd;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RecenzeID")
    private int recenzeId;
    @Basic
    @Column(name = "AlbumID")
    private int albumId;
    @Basic
    @Column(name = "BeznyuzivatelID")
    private int beznyuzivatelId;
    @Basic
    @Column(name = "KapelaID")
    private int kapelaId;
    @Basic
    @Column(name = "UmelecID")
    private int umelecId;
    @Basic
    @Column(name = "SkladbaID")
    private int skladbaId;

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getNadpis() {
        return nadpis;
    }

    public void setNadpis(String nadpis) {
        this.nadpis = nadpis;
    }

    public int getPocethvezd() {
        return pocethvezd;
    }

    public void setPocethvezd(int pocethvezd) {
        this.pocethvezd = pocethvezd;
    }

    public int getRecenzeId() {
        return recenzeId;
    }

    public void setRecenzeId(int recenzeId) {
        this.recenzeId = recenzeId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getBeznyuzivatelId() {
        return beznyuzivatelId;
    }

    public void setBeznyuzivatelId(int beznyuzivatelId) {
        this.beznyuzivatelId = beznyuzivatelId;
    }

    public int getKapelaId() {
        return kapelaId;
    }

    public void setKapelaId(int kapelaId) {
        this.kapelaId = kapelaId;
    }

    public int getUmelecId() {
        return umelecId;
    }

    public void setUmelecId(int umelecId) {
        this.umelecId = umelecId;
    }

    public int getSkladbaId() {
        return skladbaId;
    }

    public void setSkladbaId(int skladbaId) {
        this.skladbaId = skladbaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecenzeEntity that = (RecenzeEntity) o;

        if (pocethvezd != that.pocethvezd) return false;
        if (recenzeId != that.recenzeId) return false;
        if (albumId != that.albumId) return false;
        if (beznyuzivatelId != that.beznyuzivatelId) return false;
        if (kapelaId != that.kapelaId) return false;
        if (umelecId != that.umelecId) return false;
        if (skladbaId != that.skladbaId) return false;
        if (komentar != null ? !komentar.equals(that.komentar) : that.komentar != null) return false;
        if (nadpis != null ? !nadpis.equals(that.nadpis) : that.nadpis != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = komentar != null ? komentar.hashCode() : 0;
        result = 31 * result + (nadpis != null ? nadpis.hashCode() : 0);
        result = 31 * result + pocethvezd;
        result = 31 * result + recenzeId;
        result = 31 * result + albumId;
        result = 31 * result + beznyuzivatelId;
        result = 31 * result + kapelaId;
        result = 31 * result + umelecId;
        result = 31 * result + skladbaId;
        return result;
    }
}
