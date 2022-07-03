package com.epam.SongService.service.impl;

import com.epam.SongService.domain.Metadata;
import com.epam.SongService.repository.MetadataRepository;
import com.epam.SongService.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final MetadataRepository repository;

    @Override
    public Metadata createSongMetadata(Metadata metadata) {
        return repository.save(metadata);
    }

    @Override
    public Metadata getSongMetadata(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteSongMetadata(List<Long> ids) {
        repository.deleteAllById(ids);
    }
}
