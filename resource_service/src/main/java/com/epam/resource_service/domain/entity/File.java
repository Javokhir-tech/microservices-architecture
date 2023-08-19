package com.epam.resource_service.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer storageId;
    private String storageKey;
}
