package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.UzivatelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UzivatelRepository extends CrudRepository<UzivatelEntity, Integer> {
        // TODO


    @Query("SELECT u FROM UzivatelEntity u")
    public List<UzivatelEntity> getAllUzivatel();

    /*@Query("SELECT u FROM Uzivatel u WHERE u.uzivatelID = :#{uzivatel.uzivatelID}")
    public List<Uzivatel> getUzivatelByID(@Param("uzivatel") Uzivatel uzivatel);*/

    UzivatelEntity findUzivatelByUsername(String username);

    UzivatelEntity findUzivatelByEmail(String email);

    UzivatelEntity findUzivatelByUzivatelId(Integer id);

    UzivatelEntity save(UzivatelDtoIn uzivatelDtoIn);
}