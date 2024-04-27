package com.example.pawsdemo.dotIn.api;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;

import java.util.List;

public class AlbumApiDtoIn {
    private AlbumDtoIn album;
    private List<SkladbaDtoIn> skladby;
    private String umelec;

    public AlbumDtoIn getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDtoIn album) {
        this.album = album;
    }

    public List<SkladbaDtoIn> getSkladby() {
        return skladby;
    }

    public void setSkladby(List<SkladbaDtoIn> skladby) {
        this.skladby = skladby;
    }

    public String getUmelec() {
        return umelec;
    }

    public void setUmelec(String umelec) {
        this.umelec = umelec;
    }
}
