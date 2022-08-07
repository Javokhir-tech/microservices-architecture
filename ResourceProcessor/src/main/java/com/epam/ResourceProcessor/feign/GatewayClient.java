package com.epam.ResourceProcessor.feign;

import com.epam.ResourceProcessor.model.SongDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gateway-service")
public interface GatewayClient {

    @GetMapping("/resources/{resourceId}")
    ResponseEntity<byte[]> getResourceById(@PathVariable("resourceId") Integer resourceId);

    @PostMapping("/songs")
    ResponseEntity<SongDTO> createMetadata(@RequestBody SongDTO metadata);


}
