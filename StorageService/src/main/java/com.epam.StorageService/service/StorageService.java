package com.epam.StorageService.service;

import com.epam.StorageService.entity.StorageEntity;

import java.util.List;

public interface StorageService {

    Integer create(StorageEntity storageEntity);

    List<StorageEntity> getAll();

    void deleteByIds(List<Integer> ids);

    StorageEntity getById(Integer id);
}
