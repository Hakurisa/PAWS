package com.example.pawsdemo.repository;

import com.example.pawsdemo.models.Uzivatel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UzivatelRepository extends CrudRepository<Uzivatel, Integer> {
        // to-do
}
