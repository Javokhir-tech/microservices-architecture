package com.epam.ResourceService.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.epam.ResourceService.api.ResourcesApiDelegate;
import com.epam.ResourceService.domain.File;
import com.epam.ResourceService.model.FileDto;
import com.epam.ResourceService.model.ResponseIds;
import com.epam.ResourceService.repository.FileRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class ResourceService implements ResourcesApiDelegate {

    private static final String FILE_EXTENSION = "fileExtension";

    private final AmazonS3 amazonS3;
    private final String bucketName;
    private final FileRepository fileRepository;
    public ResourceService(AmazonS3 amazonS3, @Value("${config.aws.s3.bucket-name}") String bucketName, FileRepository fileRepository) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
//        initializeBucket();
        this.fileRepository = fileRepository;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<FileDto> createResource(MultipartFile file) {
        var mp3File = new File();
        mp3File.setName(file.getOriginalFilename());
        fileRepository.save(mp3File);


        amazonS3.putObject(bucketName, mp3File.getName(), file.getInputStream(), extractObjectMetadata(file));

        var fileDto = new FileDto() // mapper
                .id(mp3File.getId())
                .name(mp3File.getName());

        return ResponseEntity.ok(fileDto);
    }

    @Override
    public ResponseEntity<ResponseIds> deleteResourcesByIds(List<Integer> ids) {
        var files = fileRepository.findAllById(ids);
        for (File file : files) {
            amazonS3.deleteObject(bucketName, file.getName());
            fileRepository.delete(file);
        }
        return  ResponseEntity.ok(new ResponseIds().ids(ids));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<byte[]> getResourceById(Integer resourceId) {
        var file = fileRepository.findById(resourceId).orElseThrow(EntityNotFoundException::new);
        log.info("file {}", file);
        var content = amazonS3.getObject(bucketName, file.getName()).getObjectContent();
        return ResponseEntity.ok(IOUtils.toByteArray(content));
    }

    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        objectMetadata.getUserMetadata().put(FILE_EXTENSION, FilenameUtils.getExtension(file.getOriginalFilename()));

        return objectMetadata;
    }

//    private void initializeBucket() {
//        if (!amazonS3.doesBucketExistV2(bucketName)) {
//            amazonS3.createBucket(bucketName);
//        }
//    }
}
