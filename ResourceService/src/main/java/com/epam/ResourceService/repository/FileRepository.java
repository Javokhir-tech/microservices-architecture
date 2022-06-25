package com.epam.ResourceService.repository;

import com.epam.ResourceService.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
