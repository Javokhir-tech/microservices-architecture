package com.epam.ResourceService.rest;

import com.epam.ResourceService.api.ResourcesApi;
import com.epam.ResourceService.model.Files;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public class ResourceController implements ResourcesApi {


    @Override
    public ResponseEntity<Void> deleteResourcesByIds(List<String> resourceIds) {
        return ResourcesApi.super.deleteResourcesByIds(resourceIds);
    }

    @Override
    public ResponseEntity<Files> getResourceById(Integer resourceId) {
        return ResourcesApi.super.getResourceById(resourceId);
    }
}
