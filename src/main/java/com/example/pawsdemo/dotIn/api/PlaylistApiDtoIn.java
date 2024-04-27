package com.example.pawsdemo.dotIn.api;

import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.dotIn.SkladbaDtoIn;

import java.util.List;

public class PlaylistApiDtoIn {
    private PlaylistDtoIn playlist;
    private List<SkladbaDtoIn> skladby;
    private List<String> umelci;

    public PlaylistDtoIn getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistDtoIn playlist) {
        this.playlist = playlist;
    }

    public List<SkladbaDtoIn> getSkladby() {
        return skladby;
    }

    public void setSkladby(List<SkladbaDtoIn> skladby) {
        this.skladby = skladby;
    }

    public List<String> getUmelci() {
        return umelci;
    }

    public void setUmelci(List<String> umelci) {
        this.umelci = umelci;
    }
}
