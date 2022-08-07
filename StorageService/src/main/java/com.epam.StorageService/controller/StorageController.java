package com.epam.StorageService.controller;

import com.epam.StorageService.entity.StorageEntity;
import com.epam.StorageService.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<List<StorageEntity>> getAll() {
        return ResponseEntity.ok(storageService.getAll());
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody StorageEntity storageEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storageService.create(storageEntity));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByIds(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        storageService.deleteByIds(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
