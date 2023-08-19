package com.epam.storage_service.service;

import com.epam.storage_service.entity.StorageEntity;

import java.util.List;

public interface StorageService {

    Integer create(StorageEntity storageEntity);

    List<StorageEntity> getAll();

    void deleteByIds(List<Integer> ids);

    StorageEntity getById(Integer id);
}
