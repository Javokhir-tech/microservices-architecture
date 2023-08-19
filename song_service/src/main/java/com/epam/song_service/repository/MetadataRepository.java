package com.epam.song_service.repository;


import com.epam.song_service.domain.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
}
