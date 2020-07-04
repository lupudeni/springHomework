package com.siit.spring.mapper;

import com.siit.spring.domain.entity.AlbumEntity;
import com.siit.spring.domain.model.Album;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AlbumEntityToAlbumMapper implements Converter<AlbumEntity, Album> {

    private ForAlbumSingerEntityToSingerMapper forAlbumSingerEntityToSingerMapper;

    @Override
    public Album convert(AlbumEntity source) {
        return Album.builder()
                .id(source.getId())
                .title(source.getTitle())
                .releaseDate(source.getReleaseDate())
                .singer(forAlbumSingerEntityToSingerMapper.convert(source.getSinger()))
                .build();
    }
}
