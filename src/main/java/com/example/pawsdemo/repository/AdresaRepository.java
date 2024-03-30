package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.AdresaDtoIn;
import com.example.pawsdemo.models.AdresaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresaRepository extends CrudRepository<AdresaEntity, Integer> {
    AdresaEntity findAdresaByAdresaId(Integer id);

    AdresaEntity findAdresaByCislopopisneAndMestoAndPscAndUlice(String cislopopisne, String mesto, String psc, String ulice);

    AdresaEntity save(AdresaDtoIn adresaDtoIn);

}
