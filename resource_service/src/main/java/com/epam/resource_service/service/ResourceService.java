package com.epam.resource_service.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.epam.resource_service.Constants;
import com.epam.resource_service.api.ResourcesApiDelegate;
import com.epam.resource_service.client.StorageServiceClient;
import com.epam.resource_service.domain.entity.File;
import com.epam.resource_service.model.FileDto;
import com.epam.resource_service.model.ResponseIds;
import com.epam.resource_service.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService implements ResourcesApiDelegate {

    private final AmazonS3 amazonS3;
    private final FileRepository fileRepository;
    private final RabbitTemplate rabbitTemplate;
    private final StorageServiceClient storageServiceClient;
    private static final String FILE_EXTENSION = "fileExtension";

    @SneakyThrows
    @Override
    public ResponseEntity<FileDto> createResource(MultipartFile file) {

        var storageDto = storageServiceClient.getRandomStagingStorage();

        var mp3File = new File();
        mp3File.setName(file.getOriginalFilename());
        mp3File.setStorageId(storageDto.getId());


        var putObjectResult = amazonS3.putObject(new PutObjectRequest(storageDto.getBucket(), mp3File.getName(),
                file.getInputStream(), extractObjectMetadata(file))
        ).getETag();

        mp3File.setStorageKey(putObjectResult);
        fileRepository.save(mp3File);
        var fileDto = new FileDto() // mapper
                .id(mp3File.getId())
                .name(mp3File.getName())
                .storageId(mp3File.getStorageId());

        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY, fileDto);
        return ResponseEntity.ok(fileDto);
    }

    @Override
    public ResponseEntity<ResponseIds> deleteResourcesByIds(List<Integer> ids) {
        var files = fileRepository.findAllById(ids);
        for (File file : files) {
            var storageDto = storageServiceClient.getStorageById(file.getId());
            amazonS3.deleteObject(storageDto.getBucket(), file.getName());
            fileRepository.delete(file);
        }
        return  ResponseEntity.ok(new ResponseIds().ids(ids));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<byte[]> getResourceById(Integer resourceId) {
        var file = fileRepository.findById(resourceId).orElseThrow(EntityNotFoundException::new);
        log.info("file {}", file);
        var storageDto = storageServiceClient.getStorageById(file.getStorageId());
        log.info("storageDto {}", storageDto);
        var content = amazonS3.getObject(storageDto.getBucket(), file.getName()).getObjectContent();
//        com.amazonaws.util.IOUtils.
        return ResponseEntity.ok(IOUtils.toByteArray(content));
    }

    @Override
    @SneakyThrows
    public ResponseEntity<FileDto> updateResourceById(Integer resourceId, FileDto fileDto) {
        var file = fileRepository.findById(resourceId).orElseThrow(EntityNotFoundException::new);

//        var storageDto = storageServiceClient.getStorageById(file.getStorageId());
        file.setStorageId(file.getStorageId());
        file.setName(fileDto.getName());

        fileRepository.save(file);
        return ResponseEntity.ok(fileDto);
    }


    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.getUserMetadata().put(FILE_EXTENSION, FilenameUtils.getExtension(file.getOriginalFilename()));
        return objectMetadata;
    }

    private void initializeBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }
}
