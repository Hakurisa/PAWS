package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Au_a")
public class Au_a {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Au_aID")
    private int au_aID;

    @Column(name = "Copyright", nullable = false, length = 400)
    private String copyright;

    @ManyToOne
    @JoinColumn(name = "AlbumID", nullable = false)
    private Album album;
}
