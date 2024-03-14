package com.example.pawsdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "KapelaAu_a")
public class KapelaAu_a {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Au_aID")
    private int au_aID;

    @ManyToOne
    @JoinColumn(name = "KapelaID", nullable = false)
    private Kapela kapela;
}
