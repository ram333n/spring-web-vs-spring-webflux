package com.prokopchuk.fileservice.service.impl;

import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.fileservice.common.dto.FileDownloadDto;
import com.prokopchuk.fileservice.domain.FileData;
import com.prokopchuk.fileservice.repository.FileDataRepository;
import com.prokopchuk.fileservice.service.FileService;
import com.prokopchuk.fileservice.service.MkdirService;
import com.prokopchuk.fileservice.util.StringGenerator;
import jakarta.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFileService implements FileService {

    private static final int CODE_SIZE = 20;
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

    @Override
    public String importFile(InputStream stream, String fileName) {
        String prefix = FilenameUtils.getBaseName(fileName);
        String suffix = FilenameUtils.getExtension(fileName);

        File fileToSave = createFileToSave(prefix, suffix);

        writeFile(fileToSave, stream);

        return saveFileData(prefix, suffix, fileToSave);
    }

    private File createFileToSave(String prefix, String suffix) {
        try {
            String currentDir = getCurrentDirectoryStr();

            return Files.createTempFile(Path.of(currentDir), prefix, "." + suffix).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCurrentDirectoryStr() {
        return mkdirService.mkdirCurrentDayDirectory(storageRootFolder);
    }

    private void writeFile(File file, InputStream content) {
        try (
            BufferedInputStream input = new BufferedInputStream(content);
            FileOutputStream output = new FileOutputStream(file);
        ) {
            IOUtils.copy(input, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String saveFileData(String prefix, String suffix, File file) {
        String importCode = generateImportCode();
        FileData entity = new FileData();
        entity.setImportCode(importCode);
        entity.setPath(file.getPath());
        entity.setFilePrefix(prefix);
        entity.setFileExtension(suffix);

        fileDataRepository.save(entity);

        return importCode;
    }

    private String generateImportCode() {
        return StringGenerator.generateString(CODE_SIZE);
    }

    @Override
    public void deleteFileByImportCode(String importCode) {
        FileData fileToDeleteData = fileDataRepository.findFileByImportCode(importCode)
            .orElseThrow(() -> new NotFoundException(String.format("File with import code: %s not found", importCode)));

        File fileToDelete = new File(fileToDeleteData.getPath());
        fileToDelete.delete();

        fileDataRepository.deleteById(fileToDeleteData.getId());
    }

    @Override
    public FileDownloadDto getFileByImportCode(String importCode) {
        return fileDataRepository.findFileByImportCode(importCode)
            .map(e -> FileDownloadDto.of(e.getFilePrefix(), e.getFileExtension(), new File(e.getPath())))
            .orElseThrow(() -> new NotFoundException(String.format("File with import code: %s not found", importCode)));
    }

}
