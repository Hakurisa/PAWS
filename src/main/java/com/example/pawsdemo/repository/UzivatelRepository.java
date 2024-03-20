package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.Uzivatel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UzivatelRepository extends CrudRepository<Uzivatel, Integer> {
        // TODO

    @Query("SELECT u FROM Uzivatel u")
    public List<Uzivatel> getAllUzivatel();

    @Query("SELECT u FROM Uzivatel u WHERE u.uzivatelID = :#{uzivatel.uzivatelID}")
    public List<Uzivatel> getUzivatelByID(@Param("uzivatel") Uzivatel uzivatel);

    Uzivatel findUzivatelByUsername(String username);

    Uzivatel save(UzivatelDtoIn uzivatelDtoIn);
}
