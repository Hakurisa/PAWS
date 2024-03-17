package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "SkladbaZanr")
public class SkladbaZanr {
    @Id
    @ManyToOne
    @JoinColumn(name = "ZanrID", nullable = false)
    private Zanr zanr;

    @Id
    @ManyToOne
    @JoinColumn(name = "SkladbaID", nullable = false)
    private Skladba skladba;
}
