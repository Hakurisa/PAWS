package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.UzivatelDtoIn;
import com.example.pawsdemo.models.UzivatelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface UzivatelRepository extends CrudRepository<UzivatelEntity, Integer> {

    @Query("SELECT u FROM UzivatelEntity u")
    public List<UzivatelEntity> getAllUzivatel();

    @Query("SELECT LAST_INSERT_ID() FROM AdresaEntity")
    public int getAdresaOfUzivatel();

    @Query("SELECT LAST_INSERT_ID() FROM BeznyuzivatelEntity")
    public int getBUIdOfNewUzivatel();

    @Query("SELECT LAST_INSERT_ID() FROM UmelecEntity")
    public int getUmelecIdOfNewUzivatel();

    @Query("SELECT u.umelecId FROM UzivatelEntity u WHERE u.username = :username")
    public Integer getUmelecIdOfUzivatel(@Param("username") String username);

    UzivatelEntity findUzivatelByUsername(String username);

    UzivatelEntity findUzivatelEntityByBeznyuzivatelId(Integer id);

    @Query("SELECT u.beznyuzivatelId FROM UzivatelEntity u WHERE u.username = :username")
    public Integer getBeznyUzivatelIdOfUzivatel(@Param("username") String username);

    @Query("SELECT u.uzivatelId FROM UzivatelEntity u WHERE u.username = :username")
    public Integer getUzivatelIdOfUzivatel(@Param("username") String username);

    UzivatelEntity findUzivatelEntityByUmelecId(Integer id);

    UzivatelEntity findUzivatelByEmail(String email);
    UzivatelEntity save(UzivatelDtoIn uzivatelDtoIn);
}
