package com.example.pawsdemo.repository;

import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.AuAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuARepository extends CrudRepository<AuAEntity, Integer> {
    AuAEntity findAuAEntityByAlbumID(AlbumEntity album);
}
