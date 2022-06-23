package com.epam.SongService.rest;

import com.epam.SongService.domain.Metadata;
import com.epam.SongService.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetadatas(@PathVariable("id") long id) {
        songService.deleteSongMetadata(id);;
        return ResponseEntity.noContent().build();
    }

}
