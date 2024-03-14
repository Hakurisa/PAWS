package com.example.pawsdemo.model;

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
    private BeznyUzivatel beznyuzivatel;
}
