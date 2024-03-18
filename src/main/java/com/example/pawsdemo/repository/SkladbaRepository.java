package com.example.pawsdemo.repository;

import com.example.pawsdemo.models.Skladba;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

@Repository
public interface SkladbaRepository extends CrudRepository<Skladba, Integer>{
    public List<Skladba> getSkladbuPodleJmena(String jmeno);

    @Query("SELECT s FROM Skladba s")
    public List<Skladba> getSerazeneSkladby();

    @Query(value = "SELECT s FROM Skladba s", nativeQuery = true)
    public List<Skladba> getSerazeneSkladbyNative();

    @Query("SELECT s from Skladba s where s.jmeno LIKE %:skladbaJmeno%")
    public List<Skladba> findSkladba(@Param("jmenoSkladby") String jmenoSkladby);
}
