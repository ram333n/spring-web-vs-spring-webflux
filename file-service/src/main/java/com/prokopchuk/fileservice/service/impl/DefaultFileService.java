package com.prokopchuk.fileservice.service.impl;

import com.prokopchuk.fileservice.repository.FileDataRepository;
import com.prokopchuk.fileservice.service.MkdirService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFileService {

    private final FileDataRepository fileDataRepository;
    private final MkdirService mkdirService;

    @Value("${file.storage.root-folder}")
    private String storageRootFolder;

    @PostConstruct
    private void init() {
        mkdirService.mkdirCurrentDayDirectory(storageRootFolder);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void createDayDirectory() {
        mkdirService.mkdirCurrentDayDirectory(storageRootFolder);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    private void createMonthDirectory() {
        mkdirService.mkdirCurrentMonthDirectory(storageRootFolder);
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    private void createYearDirectory() {
        mkdirService.mkdirCurrentYearDirectory(storageRootFolder);
    }

}
