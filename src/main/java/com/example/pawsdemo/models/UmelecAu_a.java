package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "UmelecAu_a")
public class UmelecAu_a {

    @Id
    @ManyToOne
    @JoinColumn(name = "Au_aID")
    private Au_a au_a;

    @Id
    @ManyToOne
    @JoinColumn(name = "UmelecID")
    private Umelec umlec;
}
