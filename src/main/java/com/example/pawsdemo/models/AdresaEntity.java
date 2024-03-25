package com.example.pawsdemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Adresa", schema = "PAWS", catalog = "")
public class AdresaEntity {
    @Basic
    @Column(name = "Cislopopisne")
    private String cislopopisne;
    @Basic
    @Column(name = "Mesto")
    private String mesto;
    @Basic
    @Column(name = "Psc")
    private String psc;
    @Basic
    @Column(name = "Ulice")
    private String ulice;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AdresaID")
    private int adresaId;

    public String getCislopopisne() {
        return cislopopisne;
    }

    public void setCislopopisne(String cislopopisne) {
        this.cislopopisne = cislopopisne;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public int getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(int adresaId) {
        this.adresaId = adresaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdresaEntity that = (AdresaEntity) o;

        if (adresaId != that.adresaId) return false;
        if (cislopopisne != null ? !cislopopisne.equals(that.cislopopisne) : that.cislopopisne != null) return false;
        if (mesto != null ? !mesto.equals(that.mesto) : that.mesto != null) return false;
        if (psc != null ? !psc.equals(that.psc) : that.psc != null) return false;
        if (ulice != null ? !ulice.equals(that.ulice) : that.ulice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cislopopisne != null ? cislopopisne.hashCode() : 0;
        result = 31 * result + (mesto != null ? mesto.hashCode() : 0);
        result = 31 * result + (psc != null ? psc.hashCode() : 0);
        result = 31 * result + (ulice != null ? ulice.hashCode() : 0);
        result = 31 * result + adresaId;
        return result;
    }
}
