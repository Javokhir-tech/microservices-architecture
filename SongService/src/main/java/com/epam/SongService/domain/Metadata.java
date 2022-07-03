package com.epam.SongService.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Metadata {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String artist;
    private String album;
    @Column(name = "leng")
    private String length;
    @Column(name = "resourceid")
    private String resourceId;
    @Column(name = "yr")
    private Integer year;
}
