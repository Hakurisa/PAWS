package com.example.pawsdemo.repository;

import com.example.pawsdemo.dotIn.PlaylistDtoIn;
import com.example.pawsdemo.models.AlbumEntity;
import com.example.pawsdemo.models.PlaylistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {

    PlaylistEntity getAllByNazev(String nazev);

    @Query("SELECT LAST_INSERT_ID() FROM PlaylistEntity")
    public int getNewPlaylistId();

    public List<PlaylistEntity> findAll();

    //public List<PlaylistEntity> getAllByNazevOrTvurce(PlaylistDtoIn playlistDtoIn);

    PlaylistEntity save(PlaylistDtoIn playlistDtoIn);
}
