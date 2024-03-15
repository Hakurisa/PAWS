package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PlaylistSkladba")
public class PlaylistSkladba {
    @Id
    @ManyToOne
    @JoinColumn(name = "SkladbaID", nullable = false)
    private Skladba skladba;

    @Id
    @ManyToOne
    @JoinColumn(name = "PlaylistID", nullable = false)
    private Playlist playlist;
}
