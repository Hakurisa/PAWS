package com.example.pawsdemo.dotIn;

import com.example.pawsdemo.models.UzivatelEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

public class AdresaDtoIn {
    @NotNull
    private String cislopopisne;
    @NotNull
    private String mesto;
    @NotNull
    private String psc;
    @NotNull
    private String ulice;

    @Id
    @NotNull
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
}
