package com.epam.SongService.repository;


import com.epam.SongService.domain.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
}
