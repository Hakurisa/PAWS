package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class RecenzeDtoIn {

    @Id
    @NotNull
    private int recenzeId;

    @NotNull
    private String nadpis;

    @NotNull
    private String komentar;

    @NotNull
    private int pocetHvezd;

    private int albumId;
    private int beznyUzivatelId;
    private int kapelaId;
    private int UmelecId;
    private int skladbaId;

    public int getRecenzeId() {
        return recenzeId;
    }

    public void setRecenzeId(int recenzeId) {
        this.recenzeId = recenzeId;
    }

    public String getNadpis() {
        return nadpis;
    }

    public void setNadpis(String nadpis) {
        this.nadpis = nadpis;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getPocetHvezd() {
        return pocetHvezd;
    }

    public void setPocetHvezd(int pocetHvezd) {
        this.pocetHvezd = pocetHvezd;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getBeznyUzivatelId() {
        return beznyUzivatelId;
    }

    public void setBeznyUzivatelId(int beznyUzivatelId) {
        this.beznyUzivatelId = beznyUzivatelId;
    }

    public int getKapelaId() {
        return kapelaId;
    }

    public void setKapelaId(int kapelaId) {
        this.kapelaId = kapelaId;
    }

    public int getUmelecId() {
        return UmelecId;
    }

    public void setUmelecId(int umelecId) {
        UmelecId = umelecId;
    }

    public int getSkladbaId() {
        return skladbaId;
    }

    public void setSkladbaId(int skladbaId) {
        this.skladbaId = skladbaId;
    }
}
