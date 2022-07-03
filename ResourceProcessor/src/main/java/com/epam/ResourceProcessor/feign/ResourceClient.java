package com.epam.ResourceProcessor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resources-client", url = "${resource.url}")
public interface ResourceClient {

    @GetMapping("/resources/{resourceId}")
    ResponseEntity<byte[]> getResourceById(@PathVariable("resourceId") Integer resourceId);
}
