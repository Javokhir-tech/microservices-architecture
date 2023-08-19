package com.epam.song_service.service;

import com.epam.song_service.domain.Metadata;

import java.util.List;

public interface SongService {

    Metadata createSongMetadata(Metadata metadata);

    Metadata getSongMetadata(long id);

    void deleteSongMetadata(List<Long> ids);
}
