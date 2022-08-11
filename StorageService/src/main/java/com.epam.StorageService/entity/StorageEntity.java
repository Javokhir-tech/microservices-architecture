package com.epam.StorageService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "storage")
@AllArgsConstructor
@NoArgsConstructor
public class StorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StorageType storageType;

    @Column(nullable = false)
    private String bucket;

    @Column(nullable = false)
    private String path;

    enum StorageType {
        PERMANENT, STAGING
    }
}
