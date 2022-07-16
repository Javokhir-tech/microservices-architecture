package com.epam.SongService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
