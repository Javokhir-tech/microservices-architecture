package com.epam.SongService.rest;

import com.epam.SongService.containers.ContainersEnvironment;
import com.epam.SongService.domain.Metadata;
import com.epam.SongService.repository.MetadataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class SongControllerTests extends ContainersEnvironment {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Metadata metadata = Metadata.builder()
            .album("album")
            .name("name")
            .artist("artist")
            .length("2:20")
            .resourceId("1")
            .year(2020)
            .build();



    @Test
    public void createMetadataTest() throws Exception {
        // given - precondition or setup

        // when - action or behaviour that we are going test
        var response = mockMvc.perform(post("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metadata)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(metadata.getName())))
                .andExpect(jsonPath("$.album", is(metadata.getAlbum())));
    }



    @Test
    public void getMetadataByIdTest() throws Exception {



        metadataRepository.save(metadata);

        // when -  action or the behaviour that we are going test
        var response = mockMvc.perform(get("/songs/{id}", metadata.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(metadata.getName())))
                .andExpect(jsonPath("$.artist", is(metadata.getArtist())))
                .andExpect(jsonPath("$.album", is(metadata.getAlbum())));
    }

    @Test
    public void deleteById() throws Exception {
        // given - precondition or setup


        metadataRepository.save(metadata);

        // when -  action or the behaviour that we are going test
        var response = mockMvc.perform(delete("/songs?ids={ids}", metadata.getId()));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

}
