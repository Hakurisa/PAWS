package com.example.pawsdemo.repository;

import com.example.pawsdemo.models.Beznyuzivatel;
import com.example.pawsdemo.models.Uzivatel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeznyUzivatelRepository extends CrudRepository<Beznyuzivatel, Integer> {
    @Query("SELECT bu FROM Beznyuzivatel bu")
    public List<Beznyuzivatel> getAllUzivatel();

    @Query("SELECT bu FROM Beznyuzivatel bu WHERE bu.beznyuzivatelID = :#{beznyuzivatel.beznyuzivatelID}")
    public List<Beznyuzivatel> findBeznyuzivatelByBUID(@Param("beznyuzivatel") Beznyuzivatel beznyuzivatelID);
}
