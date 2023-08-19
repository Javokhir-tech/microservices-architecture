package com.epam.storage_service.service.impl;

import com.epam.storage_service.entity.StorageEntity;
import com.epam.storage_service.repository.StorageRepository;
import com.epam.storage_service.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;

    @Override
    public Integer create(StorageEntity storageEntity) {
         var result = storageRepository.save(storageEntity);
         return result.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StorageEntity> getAll() {
        return storageRepository.findAll();
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        storageRepository.deleteAllById(ids);
    }

    @Override
    public StorageEntity getById(Integer id) {
        return storageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
