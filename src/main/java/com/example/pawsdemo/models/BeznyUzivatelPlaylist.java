package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "BeznyUzivatelPlaylist")
public class BeznyUzivatelPlaylist {
    @Id
    @ManyToOne
    @JoinColumn(name = "PlaylistID", nullable = false)
    private Playlist playlist;

    @Id
    @ManyToOne
    @JoinColumn(name = "BeznyuzivatelID", nullable = false)
    private Beznyuzivatel beznyuzivatel;
}
