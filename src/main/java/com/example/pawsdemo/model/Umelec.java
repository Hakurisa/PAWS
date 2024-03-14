package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Umelec")
public class Umelec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UmelecID")
    private int umlecID;

    @Column(name = "Jmeno", nullable = false, length = 50)
    private String jmeno;

    @Column(name = "Popis", columnDefinition = "TEXT")
    private String popis;
}
