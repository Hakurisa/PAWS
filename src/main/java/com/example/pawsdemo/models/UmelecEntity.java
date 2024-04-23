package com.example.pawsdemo.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Umelec", schema = "PAWS", catalog = "")
public class UmelecEntity {
    @Basic
    @Column(name = "Jmeno")
    private String jmeno;
    @Basic
    @Column(name = "Popis")
    private String popis;
    @Basic
    @Column(name = "Clenkapely")
    private Integer clenkapely;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "UmelecID")
    private int umelecId;
//    @OneToMany(mappedBy = "umelecByUmelecId")
//    private Collection<UzivatelEntity> uzivatelsByUmelecId;

    @ManyToMany(mappedBy = "umelci")
    private Set<AlbumEntity> albums = new HashSet<>();

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

    public int getUmelecId() {
        return umelecId;
    }

    public void setUmelecId(int umelecId) {
        this.umelecId = umelecId;
    }

    public Set<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<AlbumEntity> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UmelecEntity that = (UmelecEntity) o;

        if (umelecId != that.umelecId) return false;
        if (jmeno != null ? !jmeno.equals(that.jmeno) : that.jmeno != null) return false;
        if (popis != null ? !popis.equals(that.popis) : that.popis != null) return false;
        if (clenkapely != null ? !clenkapely.equals(that.clenkapely) : that.clenkapely != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jmeno != null ? jmeno.hashCode() : 0;
        result = 31 * result + (popis != null ? popis.hashCode() : 0);
        result = 31 * result + (clenkapely != null ? clenkapely.hashCode() : 0);
        result = 31 * result + umelecId;
        return result;
    }

//    public Collection<UzivatelEntity> getUzivatelsByUmelecId() {
//        return uzivatelsByUmelecId;
//    }
//
//    public void setUzivatelsByUmelecId(Collection<UzivatelEntity> uzivatelsByUmelecId) {
//        this.uzivatelsByUmelecId = uzivatelsByUmelecId;
//    }
}
