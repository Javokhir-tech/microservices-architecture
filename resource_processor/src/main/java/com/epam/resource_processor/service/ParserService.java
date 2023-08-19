package com.epam.resource_processor.service;

import com.epam.resource_processor.model.FileDto;

public interface ParserService {

    void parseMP3ToMetadata(FileDto fileDto);
}
