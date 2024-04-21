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
//TODO    @Query("SELECT u FROM Umelec u WHERE u.Jmeno LIKE %searchInput%")
//    public List<UmelecEntity> getAllresults();

    UmelecEntity findUmelecEntityByUmelecId(Integer id);

    UmelecEntity save(UmelecDtoIn umelecDtoIn);

    @Query("SELECT LAST_INSERT_ID() FROM UzivatelEntity")
    public int getUzivatelIdOfNewUmelec();

    List<AlbumEntity> findAlbumsByUmelecId(int umelecId);

}
