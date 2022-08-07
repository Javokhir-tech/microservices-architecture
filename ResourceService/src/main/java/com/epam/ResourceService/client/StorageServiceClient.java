package com.epam.ResourceService.client;

import com.epam.ResourceService.domain.model.StorageDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.epam.ResourceService.domain.model.StorageDTO.StorageType.PERMANENT;
import static com.epam.ResourceService.domain.model.StorageDTO.StorageType.STAGING;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageServiceClient {

    private final RestTemplate restTemplate;

    @Value("${storage.application.name}")
    private String storageAppName;

    public StorageDTO getRandomStagingStorage() {
        StorageDTO[] storages = getStorages();
        log.info("storages {}", storages);
        List<StorageDTO> storageList = new ArrayList<>();

        for (StorageDTO storage : storages) {
            if (STAGING.equals(storage.getStorageType())) {
                storageList.add(storage);
            }
        }

        int randomNumber = getRandomNumber(0, storageList.size() - 1);
        return storageList.get(randomNumber);
    }

    @CircuitBreaker(name = "cb-instanceA", fallbackMethod = "localCacheGetStorageById")
    public StorageDTO getStorageById(Integer id) {
        final String URL_STORAGE_BY_ID = "http://" + storageAppName + "/storages/" + id;
        log.info("URL_STORAGE_BY_ID {}", URL_STORAGE_BY_ID);
        return restTemplate.getForObject(URL_STORAGE_BY_ID, StorageDTO.class);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    private StorageDTO[] getStorages() {
        final String URL_STORAGES = "http://" + storageAppName + "/storages";
        log.info("URL_STORAGES: " + URL_STORAGES);
        return restTemplate.getForObject(URL_STORAGES, StorageDTO[].class);
    }

    public StorageDTO localCacheGetStorageById(Integer id, Exception exception) {
        log.info("localCacheGetStorageById method: have input");
        return StorageDTO.builder()
                .id(2)
                .storageType(PERMANENT)
                .bucket("resource")
                .build();
    }

}
