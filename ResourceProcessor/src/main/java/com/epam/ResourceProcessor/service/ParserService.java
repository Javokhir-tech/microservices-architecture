package com.epam.ResourceProcessor.service;

import com.epam.ResourceProcessor.model.FileDto;
import com.epam.ResourceProcessor.model.SongDTO;

public interface ParserService {

    void parseMP3ToMetadata(FileDto fileDto);
}
