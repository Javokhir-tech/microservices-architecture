package com.epam.ResourceService.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageDTO {

    private Integer id;
    private StorageType storageType;
    private String bucket;
    private String path;

    public enum StorageType {
        STAGING, PERMANENT
    }
}
