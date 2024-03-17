package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Adresa")
public class Adresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdresaID")
    private int adresaID;

    @Column(name = "Cislopopisne", nullable = false, length = 20)
    private String cislopopisne;

    @Column(name = "Mesto", nullable = false, length = 85)
    private String mesto;

    @Column(name = "Psc", nullable = false, length = 10)
    private String psc;

    @Column(name = "Ulice", length = 50)
    private String ulice;
}
