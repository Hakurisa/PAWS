package com.example.pawsdemo.repository;


import com.example.pawsdemo.models.ZanrEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZanrRepository extends CrudRepository<ZanrEntity, Integer> {



}
