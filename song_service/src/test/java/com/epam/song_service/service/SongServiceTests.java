package com.epam.song_service.service;

import com.epam.song_service.containers.ContainersEnvironment;
import com.epam.song_service.domain.Metadata;
import com.epam.song_service.repository.MetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SongServiceTests extends ContainersEnvironment {

    @MockBean
    private MetadataRepository metadataRepository;

    @Autowired
    private SongService songService;

    Metadata metadata;
    @BeforeEach
    void setUp() {
        Metadata metadata =  Metadata.builder()
                .id(22l)
                .album("album")
                .year(2020)
                .name("name")
                .artist("artist")
                .build();

        when(metadataRepository.findById(22l))
                .thenReturn(Optional.ofNullable(metadata));
    }

    @Test
    public void whenValidId_thenMetadataShouldBeFound() {
        var metadataId = 22;
        var found = songService.getSongMetadata(metadataId);

        assertThat(found.getId()).isEqualTo(metadataId);
    }

    @Test
    public void whenValidBody_thenMetadataShouldBeCreated() {
        var created = songService.createSongMetadata(metadata);

        when(created).thenReturn(metadata);
    }

}
