package com.example.pawsdemo.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Beznyuzivatel", schema = "PAWS", catalog = "")
public class BeznyuzivatelEntity {
    @Basic
    @Column(name = "Jmeno")
    private String jmeno;
    @Basic
    @Column(name = "Oblibenezanry")
    private Integer oblibenezanry;
    @Basic
    @Column(name = "Prijmeni")
    private String prijmeni;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BeznyuzivatelID")
    private int beznyuzivatelId;

    @ManyToMany(mappedBy = "uzivatele")
    private Set<BeznyuzivatelEntity> playlists = new HashSet<>();


    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public Integer getOblibenezanry() {
        return oblibenezanry;
    }

    public void setOblibenezanry(Integer oblibenezanry) {
        this.oblibenezanry = oblibenezanry;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public int getBeznyuzivatelId() {
        return beznyuzivatelId;
    }

    public void setBeznyuzivatelId(int beznyuzivatelId) {
        this.beznyuzivatelId = beznyuzivatelId;
    }

    public Set<BeznyuzivatelEntity> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<BeznyuzivatelEntity> playlists) {
        this.playlists = playlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeznyuzivatelEntity that = (BeznyuzivatelEntity) o;

        if (beznyuzivatelId != that.beznyuzivatelId) return false;
        if (jmeno != null ? !jmeno.equals(that.jmeno) : that.jmeno != null) return false;
        if (oblibenezanry != null ? !oblibenezanry.equals(that.oblibenezanry) : that.oblibenezanry != null)
            return false;
        if (prijmeni != null ? !prijmeni.equals(that.prijmeni) : that.prijmeni != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jmeno != null ? jmeno.hashCode() : 0;
        result = 31 * result + (oblibenezanry != null ? oblibenezanry.hashCode() : 0);
        result = 31 * result + (prijmeni != null ? prijmeni.hashCode() : 0);
        result = 31 * result + beznyuzivatelId;
        return result;
    }
}
