package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<AlbumEntity, Integer> {

    //AlbumEntity findAlbumEntityByAlbumId(Integer id);

    // AlbumEntity save(AlbumDtoIn albumDtoIn);

    //AlbumEntity updateAlbumEntityByAlbumId(AlbumDtoIn albumDto);

    //@Query("SELECT s FROM SkladbaEntity s WHERE SkladbaEntity.albumId = AlbumEntity.albumId")
    //public int findAllSkladbyByAlbumId();

    //List<AlbumEntity> findAlbumEntitiesByNazev();
    public List<AlbumEntity> findAll();
}
