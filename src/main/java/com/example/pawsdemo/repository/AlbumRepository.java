package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.AlbumDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<AlbumEntity, Integer> {

    AlbumEntity findAlbumEntityByAlbumId(Integer id);

    AlbumEntity save(AlbumDtoIn albumDtoIn);
}
