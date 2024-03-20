package com.example.pawsdemo.repository;

import com.example.pawsdemo.models.Umelec;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmelecRepository extends CrudRepository<Umelec, Integer> {
    Umelec findByEmail(String UmelecID);
}
