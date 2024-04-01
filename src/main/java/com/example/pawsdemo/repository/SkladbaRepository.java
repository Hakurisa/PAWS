package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.SkladbaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkladbaRepository extends CrudRepository<SkladbaEntity, Integer> {
    SkladbaEntity findSkladbaEntityBySkladbaId(Integer id);

    SkladbaEntity save(SkladbaDtoIn skladbaDtoIn);
}
