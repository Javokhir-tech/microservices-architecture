package com.epam.resource_processor.model;

import lombok.Data;

@Data
public class SongDTO {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String length;
    private Integer resourceId;
    private Integer year;
}
