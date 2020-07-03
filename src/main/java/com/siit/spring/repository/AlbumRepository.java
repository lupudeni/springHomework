package com.siit.spring.repository;

import com.siit.spring.domain.entity.AlbumEntity;

import com.siit.spring.domain.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Album findAlbumEntityById(long albumId);
}
