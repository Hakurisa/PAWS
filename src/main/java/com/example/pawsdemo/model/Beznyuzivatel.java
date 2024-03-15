package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Beznyuzivatel")
public class Beznyuzivatel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BeznyuzivatelID")
    private int beznyuzivatelID;

    @Column(name = "Jmeno", nullable = false, length = 25)
    private String jmeno;

    @Column(name = "Oblibenezanry")
    private Integer oblibenezanry;

    @Column(name = "Prijmeni", length = 25)
    private String prijmeni;
}
