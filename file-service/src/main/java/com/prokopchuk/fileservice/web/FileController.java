package com.prokopchuk.fileservice.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.commons.exception.OperationException;
import com.prokopchuk.fileservice.service.FileService;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/is-alive")
    public ApiResponse<String> isAlive() {
        return Responses.ok("alive");
    }

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

}
