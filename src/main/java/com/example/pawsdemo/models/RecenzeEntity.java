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
    private Integer albumId;
    @Basic
    @Column(name = "BeznyuzivatelID")
    private int beznyuzivatelId;
    @Basic
    @Column(name = "KapelaID")
    private Integer kapelaId;
    @Basic
    @Column(name = "UmelecID")
    private Integer umelecId;
    @Basic
    @Column(name = "SkladbaID")
    private Integer skladbaId;

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

    public int getBeznyuzivatelId() {
        return beznyuzivatelId;
    }

    public void setBeznyuzivatelId(int beznyuzivatelId) {
        this.beznyuzivatelId = beznyuzivatelId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getKapelaId() {
        return kapelaId;
    }

    public void setKapelaId(Integer kapelaId) {
        this.kapelaId = kapelaId;
    }

    public Integer getUmelecId() {
        return umelecId;
    }

    public void setUmelecId(Integer umelecId) {
        this.umelecId = umelecId;
    }

    public Integer getSkladbaId() {
        return skladbaId;
    }

    public void setSkladbaId(Integer skladbaId) {
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
