package com.example.pawsdemo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Zanr", schema = "PAWS", catalog = "")
public class ZanrEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZanrID", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "Anotace", nullable = false, length = 100)
    private String anotace;

    @Size(max = 25)
    @NotNull
    @Column(name = "Nazev", nullable = false, length = 25)
    private String nazev;

    @ManyToMany(mappedBy = "zanry")
    private Set<SkladbaEntity> skladby = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnotace() {
        return anotace;
    }

    public void setAnotace(String anotace) {
        this.anotace = anotace;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Set<SkladbaEntity> getSkladby() {
        return skladby;
    }

    public void setSkladby(Set<SkladbaEntity> skladby) {
        this.skladby = skladby;
    }
}