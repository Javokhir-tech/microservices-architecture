package com.epam.ResourceProcessor.service.imol;

import com.epam.ResourceProcessor.Constants;
import com.epam.ResourceProcessor.feign.GatewayClient;
import com.epam.ResourceProcessor.model.FileDto;
import com.epam.ResourceProcessor.model.SongDTO;
import com.epam.ResourceProcessor.model.StorageDTO;
import com.epam.ResourceProcessor.restclient.StorageServiceClient;
import com.epam.ResourceProcessor.service.ParserService;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    private final GatewayClient gatewayClient;
    private final RestTemplate restTemplate;
    private final StorageServiceClient storageServiceClient;

    // Getting the file via creating File class object
    static String FILEPATH = "file";
    static File file = new File(FILEPATH);


    @Value("${resource.application.name}")
    private final String resourceAppName;


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @SneakyThrows
    @Override
    @RabbitListener(queues = Constants.QUEUE)
    public void parseMP3ToMetadata(FileDto fileDto) {
        System.out.println("Message Received from queue: " + fileDto);
        var resourceFile =  gatewayClient.getResourceById(fileDto.getId());

        writeByte(resourceFile.getBody());

        Mp3File mp3File = new Mp3File(file);

        var songDto = new SongDTO();
        fillMp3Metadata(songDto, mp3File.getId3v2Tag());
        songDto.setResourceId(fileDto.getId());
        log.info("song {}", songDto);

        var payloadResp = gatewayClient.createMetadata(songDto);
        if (payloadResp.getStatusCode().is2xxSuccessful())
            updateResource(fileDto);
        log.info("payloadResp {}", payloadResp);
//        return songDto;
    }

    public void updateResource(FileDto fileDto) {
        var storage = storageServiceClient.getRandomStagingStorage();
        fileDto.setId(storage.getId());
        final String URL_RESOURCE_UPDATE_BY_ID = "http://" + resourceAppName + "/resources/" + fileDto.getId();
        log.info("updateResource starting...");
        restTemplate.put(URL_RESOURCE_UPDATE_BY_ID, fileDto);
        log.info("updateResource end.");
    }



    static void writeByte(byte[] bytes) {

        // Try block to check for exceptions
        try {
            // Initialize a pointer in file
            // using OutputStream
            OutputStream os = new FileOutputStream(file);

            // Starting writing the bytes in it
            os.write(bytes);
            // Display message onconsole for successful
            // execution
            System.out.println("Successfully"
                    + " byte inserted");
            // Close the file connections
            os.close();
        }
        // Catch block to handle the exceptions
        catch (Exception e) {

            // Display exception on console
            System.out.println("Exception: " + e);
        }
    }
    private void fillMp3Metadata(SongDTO songDTO, ID3v2 id3v2Tag) {
        songDTO.setName(String.format("%s - %s", id3v2Tag.getArtistUrl(), id3v2Tag.getTitle()));
        songDTO.setArtist(id3v2Tag.getAlbumArtist());
        songDTO.setAlbum(id3v2Tag.getAlbum());
        songDTO.setLength(String.valueOf(id3v2Tag.getLength()));
        songDTO.setYear(Integer.parseInt(id3v2Tag.getYear()));
    }

    @SneakyThrows
    private File convertMultiPartToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        return convertedFile;
    }
}
