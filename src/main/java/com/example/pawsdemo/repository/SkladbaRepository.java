package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.SkladbaDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SkladbaRepository extends CrudRepository<SkladbaEntity, Integer> {
    SkladbaEntity findSkladbaEntityBySkladbaId(Integer id);

    SkladbaEntity save(SkladbaDtoIn skladbaDtoIn);

    List<SkladbaEntity> findSkladbaEntityByAlbumId(Integer albumId);

    List<SkladbaEntity> getSkladbaEntitiesByAlbumId(Integer albumId);

    @Query("SELECT skladba FROM SkladbaEntity skladba JOIN skladba.playlists playlist WHERE playlist.playlistId = :playlistId")
    List<SkladbaEntity> findByPlaylistId(@Param("playlistId") Integer playlistId);

    @Query("SELECT album FROM AlbumEntity album JOIN SkladbaEntity skladba ON album.albumId = skladba.albumId WHERE skladba.skladbaId = :skladbaId")
    AlbumEntity findAlbumEntityBySkladbaId(@Param("skladbaId") Integer skladbaId);

    @Query("SELECT skladba FROM SkladbaEntity skladba WHERE LOWER(skladba.jmeno) LIKE (LOWER(CONCAT('%', :name, '%')))")
    List<SkladbaEntity> findSkladbaEntitiesByPartialJmeno(String name);

    List<SkladbaEntity> findSkladbaEntitiesByJmeno(String name);
}

