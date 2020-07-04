package com.siit.spring.repository;

import com.siit.spring.domain.entity.AlbumEntity;

import com.siit.spring.domain.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Album findAlbumEntityById(long albumId);

    @Query("SELECT a from AlbumEntity a " +
            "LEFT JOIN FETCH a.singer " +
            "ORDER BY a.title")
    List<AlbumEntity> getAll();


}

