package com.epam.SongService.service;

import com.epam.SongService.domain.Metadata;

import java.util.List;

public interface SongService {

    Metadata createSongMetadata(Metadata metadata);

    Metadata getSongMetadata(long id);

    void deleteSongMetadata(List<Long> ids);
}
