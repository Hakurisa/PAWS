package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.models.RecenzeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecenzeRepository extends CrudRepository<RecenzeEntity, Integer> {

    List<RecenzeEntity> getRecenzeEntityByAlbumId(Integer id);

    List<RecenzeEntity> getRecenzeEntityByUmelecId(Integer id);
    RecenzeEntity save(RecenzeDtoIn recenzeDtoIn);
    RecenzeEntity deleteRecenzeEntityByRecenzeId(Integer id);
}
