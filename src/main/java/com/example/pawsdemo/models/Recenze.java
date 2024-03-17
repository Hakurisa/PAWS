package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Recenze")
public class Recenze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecenzeID")
    private int recenzeID;

    @Column(name = "Komentar", columnDefinition = "TEXT")
    private String komentar;

    @Column(name = "Nadpis", nullable = false, length = 50)
    private String nadpis;

    @Column(name = "Pocethvezd", nullable = false)
    private int pocethvezd;

    @ManyToOne
    @JoinColumn(name = "AlbumID", nullable = false)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "BeznyuzivatelID", nullable = false)
    private Beznyuzivatel beznyuzivatel;

    @ManyToOne
    @JoinColumn(name = "KapelaID", nullable = false)
    private Kapela kapela;

    @ManyToOne
    @JoinColumn(name = "UmelecID", nullable = false)
    private Umelec umlec;

    @ManyToOne
    @JoinColumn(name = "SkladbaID", nullable = false)
    private Skladba skladba;

}
