package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.UmelecDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.UmelecEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmelecRepository extends CrudRepository<UmelecEntity, Integer> {
    UmelecEntity findUmelecEntityByUmelecId(Integer id);

    UmelecEntity save(UmelecDtoIn umelecDtoIn);
}
