package com.example.pawsdemo.repository;

import com.example.pawsdemo.models.BeznyuzivatelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BURepository extends CrudRepository<BeznyuzivatelEntity, Integer> {
    BeznyuzivatelEntity findBeznyuzivatelEntityByBeznyuzivatelId(Integer id);

    @Query("SELECT LAST_INSERT_ID() FROM UzivatelEntity")
    public int getUzivatelIdOfNewBU();
}
