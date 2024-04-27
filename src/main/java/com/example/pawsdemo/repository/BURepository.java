package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.models.BeznyuzivatelEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BURepository extends CrudRepository<BeznyuzivatelEntity, Integer> {
    public BeznyuzivatelEntity findBeznyuzivatelEntityByBeznyuzivatelId(Integer id);

    @Query("SELECT LAST_INSERT_ID() FROM UzivatelEntity")
    public int getUzivatelIdOfNewBU(Integer id);

    BeznyuzivatelEntity save(BeznyUzivatelDotIn beznyUzivatelDot);

}
