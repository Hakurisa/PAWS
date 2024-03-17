package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Zanr")
public class Zanr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZanrID")
    private int zanrID;

    @Column(name = "Anotace", nullable = false, length = 100)
    private String anotace;

    @Column(name = "Nazev", nullable = false, length = 25)
    private String nazev;
}
