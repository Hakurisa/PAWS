package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Kapela")
public class Kapela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KapelaID")
    private int kapelaID;

    @Column(name = "Jmeno", nullable = false, length = 50)
    private String jmeno;

    @Column(name = "Popis", columnDefinition = "TEXT")
    private String popis;
}
