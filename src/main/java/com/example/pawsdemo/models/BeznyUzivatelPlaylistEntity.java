package com.example.pawsdemo.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "beznyUzivatelPlaylist", schema = "PAWS", catalog = "")
public class BeznyUzivatelPlaylistEntity {
    @Id
    private int id;
    @Basic
    @Column(name = "PlaylistID")
    private int playlistId;
    @Basic
    @Column(name = "BeznyuzivatelID")
    private int beznyuzivatelId;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getBeznyuzivatelId() {
        return beznyuzivatelId;
    }

    public void setBeznyuzivatelId(int beznyuzivatelId) {
        this.beznyuzivatelId = beznyuzivatelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeznyUzivatelPlaylistEntity that = (BeznyUzivatelPlaylistEntity) o;

        if (playlistId != that.playlistId) return false;
        if (beznyuzivatelId != that.beznyuzivatelId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = playlistId;
        result = 31 * result + beznyuzivatelId;
        return result;
    }
}
