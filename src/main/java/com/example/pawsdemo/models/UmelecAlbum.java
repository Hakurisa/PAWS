package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "UmelecAlbum")
public class UmelecAlbum {

    @Id
    @ManyToOne
    @JoinColumn(name = "AlbumID", nullable = false)
    private Album album;

    @Id
    @ManyToOne
    @JoinColumn(name = "UmelecID", nullable = false)
    private Umelec umlec;
}
