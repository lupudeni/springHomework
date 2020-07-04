package com.siit.spring.service;

import com.siit.spring.domain.entity.AlbumEntity;
import com.siit.spring.domain.entity.SingerEntity;
import com.siit.spring.domain.model.Album;
import com.siit.spring.exception.AlbumNotFoundException;
import com.siit.spring.exception.BadRequestException;
import com.siit.spring.exception.SingerNotFoundException;
import com.siit.spring.mapper.AlbumEntityToAlbumMapper;
import com.siit.spring.mapper.AlbumToAlbumEntityMapper;
import com.siit.spring.repository.AlbumRepository;
import com.siit.spring.repository.SingerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final AlbumEntityToAlbumMapper albumEntityToAlbumMapper;

    private final AlbumToAlbumEntityMapper albumToAlbumEntityMapper;

    private final SingerRepository singerRepository;

    public Album create (Album album) {
        long singerId = album.getSinger().getId();
        Optional<SingerEntity> singerEntityOptional = singerRepository.findById(singerId);
        SingerEntity singerEntity = singerEntityOptional.orElseThrow(() -> new SingerNotFoundException("Singer with id " + singerId + " not found"));
        AlbumEntity albumEntity = albumToAlbumEntityMapper.convert(album);
        albumEntity.setSinger(singerEntity);
        AlbumEntity savedEntity = albumRepository.save(albumEntity);
        return albumEntityToAlbumMapper.convert(savedEntity);
    }

    public Album findById(long albumId) {
        return albumRepository.findById(albumId)
                .map(albumEntityToAlbumMapper::convert)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id " + albumId + " not found"));
    }

    public List<Album> getAllAlbums() {
        return albumRepository.getAll().stream()
                .map(albumEntityToAlbumMapper::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Album album) {
        if (album.getSinger() == null) {
            throw new BadRequestException("Field id for singer cannot pe null");
        }
        AlbumEntity existingAlbumEntity = albumRepository.findById(album.getId())
                .orElseThrow(() -> new AlbumNotFoundException("No such album found"));
        updateFields(existingAlbumEntity, album);
    }

    private void updateFields(AlbumEntity existingAlbumEntity, Album album) {
        long singerId = album.getSinger().getId();
        SingerEntity singerEntity = singerRepository.findById(singerId)
                .orElseThrow(() -> new SingerNotFoundException("No singer exists for id: " + singerId));

        if (album.getTitle() != null) {
            existingAlbumEntity.setTitle(album.getTitle());
        }

        if(album.getReleaseDate() != null) {
            existingAlbumEntity.setReleaseDate(album.getReleaseDate());
        }

        existingAlbumEntity.setSinger(singerEntity);
    }

    public void delete(long albumId) {
        albumRepository.deleteById(albumId);
    }





}
