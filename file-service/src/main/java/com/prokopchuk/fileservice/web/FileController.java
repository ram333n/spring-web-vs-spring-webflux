package com.prokopchuk.fileservice.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.commons.exception.OperationException;
import com.prokopchuk.fileservice.common.dto.FileDownloadDto;
import com.prokopchuk.fileservice.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest.Headers;

@Log4j2
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/import")
    public ApiResponse<String> importFile(@RequestPart("file") MultipartFile file, @RequestPart String dto) {
        log.info("importFile");
        try (InputStream inputStream = file.getInputStream()) {
            String importCode = fileService.importFile(inputStream, file.getOriginalFilename());

            return Responses.ok(importCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/by-import-code/{import-code}")
    public ApiResponse<Void> deleteByImportCode(@PathVariable("import-code") String importCode) {
        log.info("Request on deleting file by import code. Import code: {}", importCode);
        fileService.deleteFileByImportCode(importCode);

        return Responses.ok();
    }

    @GetMapping("/download/by-import-code/{import-code}")
    public ResponseEntity<Resource> downloadFileByImportCode(@PathVariable("import-code") String importCode, HttpServletResponse response) {
        log.info("Request on downloading file by import code. Import code: {}", importCode);
        FileDownloadDto fileToDownload = fileService.getFileByImportCode(importCode);

        return setFileDownloadResponse(fileToDownload);
    }

    private ResponseEntity<Resource> setFileDownloadResponse(FileDownloadDto fileData) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileData.getFullFileName()))
            .body(new FileSystemResource(fileData.getFile()));
    }

}
