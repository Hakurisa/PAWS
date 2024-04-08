package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BeznyUzivatelDotIn {
    @Id
    @NotNull
    private int beznyuzivatelID;
    @NotNull
    private String jmeno;
    @NotNull
    private String prijmeni;
    private Integer oblibeneZanry;

    public int getBeznyuzivatelID() {
        return beznyuzivatelID;
    }

    public void setBeznyuzivatelID(int beznyuzivatelID) {
        this.beznyuzivatelID = beznyuzivatelID;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public Integer getOblibeneZanry() {
        return oblibeneZanry;
    }

    public void setOblibeneZanry(Integer oblibeneZanry) {
        this.oblibeneZanry = oblibeneZanry;
    }
}
