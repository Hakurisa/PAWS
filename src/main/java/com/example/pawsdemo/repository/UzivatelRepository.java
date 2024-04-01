package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.AdresaEntity;
import com.example.pawsdemo.models.UzivatelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UzivatelRepository extends CrudRepository<UzivatelEntity, Integer> {


    @Query("SELECT u FROM UzivatelEntity u")
    public List<UzivatelEntity> getAllUzivatel();

    @Query("SELECT LAST_INSERT_ID() FROM AdresaEntity")
    public int getAdresaOfUzivatel();

    UzivatelEntity findUzivatelByUsername(String username);

    UzivatelEntity findUzivatelByEmail(String email);

    UzivatelEntity findUzivatelByUzivatelId(Integer id);

    UzivatelEntity save(UzivatelDtoIn uzivatelDtoIn);
}
