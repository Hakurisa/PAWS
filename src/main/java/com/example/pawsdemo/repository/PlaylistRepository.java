package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import com.example.pawsdemo.models.SkladbaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {

    @Query("SELECT LAST_INSERT_ID() FROM PlaylistEntity")
    public int getNewPlaylistId();

    public List<PlaylistEntity> findAll();

    public List<PlaylistEntity> findAllByTvurce(String tvurce);

    public List<PlaylistEntity> findAllBySkladbas(List<SkladbaEntity> skladbas);

    //public List<PlaylistEntity> getAllByNazevOrTvurce(PlaylistDtoIn playlistDtoIn);
    PlaylistEntity save(PlaylistDtoIn playlistDtoIn);

    @Query("SELECT playlist from PlaylistEntity playlist JOIN playlist.skladbas skladba WHERE skladba.skladbaId = :skladbaId")
    List<PlaylistEntity> findPlaylistEntitiesBySkladbaId(Integer skladbaId);
}
