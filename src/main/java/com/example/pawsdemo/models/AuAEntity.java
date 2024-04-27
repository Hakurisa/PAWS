package com.example.pawsdemo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Au_a")
public class AuAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Au_aID", nullable = false)
    private Integer id;

    @Size(max = 1100)
    @NotNull
    @Column(name = "Copyright", nullable = false, length = 1100)
    private String copyright;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AlbumID", nullable = false)
    private AlbumEntity albumID;

    @ManyToMany
    @JoinTable(name = "UmelecAu_a",
            joinColumns = @JoinColumn(name = "Au_aID"),
            inverseJoinColumns = @JoinColumn(name = "UmelecID"))
    private Set<UmelecEntity> umelciAu = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public AlbumEntity getAlbumID() {
        return albumID;
    }

    public void setAlbumID(AlbumEntity albumID) {
        this.albumID = albumID;
    }

    public Set<UmelecEntity> getUmelci() {
        return umelciAu;
    }

    public void setUmelci(Set<UmelecEntity> umelci) {
        this.umelciAu = umelci;
    }

}