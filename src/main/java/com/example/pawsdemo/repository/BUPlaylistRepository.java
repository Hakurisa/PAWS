package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.BeznyUzivatelDotIn;
import com.example.pawsdemo.models.BeznyUzivatelPlaylistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BUPlaylistRepository extends CrudRepository<BeznyUzivatelPlaylistEntity, Integer>{

    // BeznyUzivatelPlaylistEntity findBeznyUzivatelPlaylistEntityByBeznyuzivatelId(Integer id);
    BeznyUzivatelPlaylistEntity save(BeznyUzivatelDotIn beznyUzivatelDotIn);

}
