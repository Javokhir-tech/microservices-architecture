package com.epam.ResourceProcessor.feign;

import com.epam.ResourceProcessor.model.SongDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "song-client", url = "${song.url}")
public interface SongClient {

    @PostMapping("/songs")
    ResponseEntity<SongDTO> createMetadata(@RequestBody SongDTO metadata);
}
