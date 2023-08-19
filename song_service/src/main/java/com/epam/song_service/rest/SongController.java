package com.epam.song_service.rest;

import com.epam.song_service.domain.Metadata;
import com.epam.song_service.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Metadata> createMetadata(@RequestBody Metadata metadata) {
        return ResponseEntity.ok(songService.createSongMetadata(metadata));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metadata> getMetadata(@PathVariable("id") long id) {
        return ResponseEntity.ok(songService.getSongMetadata(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMetadatas(@RequestParam(value = "ids", required = true) List<Long> ids) {
        songService.deleteSongMetadata(ids);
        return ResponseEntity.noContent().build();
    }

}
