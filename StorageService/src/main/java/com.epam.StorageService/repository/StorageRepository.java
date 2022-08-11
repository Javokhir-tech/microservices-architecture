package com.epam.StorageService.repository;

import com.epam.StorageService.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
}
