package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.RecenzeDtoIn;
import com.example.pawsdemo.models.RecenzeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecenzeRepository extends CrudRepository<RecenzeEntity, Integer> {

    public int findRecenzeEntityByAlbumId(Integer id);
    public int findRecenzeEntityBySkladbaId(Integer id);
    public int findRecenzeEntityByKapelaId(Integer id);
    public int findRecenzeEntityByUmelecId(Integer id);

    RecenzeEntity save(RecenzeDtoIn recenzeDtoIn);
    RecenzeEntity deleteRecenzeEntityByRecenzeId(Integer id);
}
