package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<AlbumEntity, Integer> {

    AlbumEntity findAlbumEntityByAlbumId(Integer id);

    // AlbumEntity save(AlbumDtoIn albumDtoIn);

    //AlbumEntity updateAlbumEntityByAlbumId(AlbumDtoIn albumDto);

    //@Query("SELECT s FROM SkladbaEntity s WHERE SkladbaEntity.albumId = AlbumEntity.albumId")
    //public int findAllSkladbyByAlbumId();

    List<AlbumEntity> findAlbumEntitiesByNazev(String name);

    public List<AlbumEntity> findAll();

    public List<AlbumEntity> findAllByPublikovano(Byte status);

    @Query("SELECT a from AlbumEntity a WHERE LOWER(a.nazev) LIKE LOWER(CONCAT('%', :nazev, '%'))")
    List<AlbumEntity> findAlbumEntitiesByPartialNazev(String nazev);
}
