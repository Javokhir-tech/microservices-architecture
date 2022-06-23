package com.epam.ResourceService.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.epam.ResourceService.api.ResourcesApiDelegate;
import com.epam.ResourceService.domain.File;
import com.epam.ResourceService.model.Files;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
public class ResourceService implements ResourcesApiDelegate {

    private static final String FILE_EXTENSION = "fileExtension";

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public ResourceService(AmazonS3 amazonS3, @Value("${config.aws.s3.bucket-name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;

//        initializeBucket();
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Files> createResource(MultipartFile file) {
        var mp3File = new File();
        mp3File.setUrl(file.getOriginalFilename());

        var files = new Files()
                .id(mp3File.getId())
                .url(mp3File.getUrl());

        amazonS3.putObject(bucketName, files.getUrl(), file.getInputStream(), extractObjectMetadata(file));

        return ResponseEntity.ok(files);
    }

    @Override
    public ResponseEntity<Void> deleteResourcesByIds(List<String> resourceIds) {
        return ResourcesApiDelegate.super.deleteResourcesByIds(resourceIds);
    }

    @Override
    public ResponseEntity<Files> getResourceById(Integer resourceId) {
        return ResourcesApiDelegate.super.getResourceById(resourceId);
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
