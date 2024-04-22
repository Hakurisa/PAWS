package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.RecenzeDotIn;
import com.example.pawsdemo.models.RecenzeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecenzeRepository extends CrudRepository<RecenzeEntity, Integer> {
    RecenzeEntity save(RecenzeDotIn recenzeDotIn);

    RecenzeEntity deleteRecenzeEntityByRecenzeId(Integer id);
}
