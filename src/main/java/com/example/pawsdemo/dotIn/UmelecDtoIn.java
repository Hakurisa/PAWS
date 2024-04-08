package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class UmelecDtoIn {

    @Id
    @NotNull
    private int umelecID;

    @NotNull
    private String jmeno;

    @NotNull
    private String popis;

    private Integer clenkapely;

    public int getUmelecID() {
        return umelecID;
    }

    public void setUmelecID(int umelecID) {
        this.umelecID = umelecID;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Integer getClenkapely() {
        return clenkapely;
    }

    public void setClenkapely(Integer clenkapely) {
        this.clenkapely = clenkapely;
    }
}
