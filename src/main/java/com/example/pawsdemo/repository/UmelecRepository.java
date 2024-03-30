package com.example.pawsdemo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// @Repository
public interface UmelecRepository /* implements CrudRepository<UmelecEntity, Integer> */ {
    //TODO
//    @Query("SELECT u FROM Umelec u WHERE u.Jmeno LIKE %searchInput%")
//    public List<UmelecEntity> getAllresults();
}
