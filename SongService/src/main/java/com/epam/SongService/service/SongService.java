package com.epam.SongService.service;

import com.epam.SongService.domain.Metadata;

public interface SongService {

    Metadata createSongMetadata(Metadata metadata);

    Metadata getSongMetadata(long id);

    void deleteSongMetadata(long id);
}
