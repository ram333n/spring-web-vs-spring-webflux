package com.prokopchuk.reactivenoteapp.service.impl;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.reactivenoteapp.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class DefaultFileService implements FileService {

    private final WebClient fileServiceWebClient;

    public DefaultFileService(@Qualifier("fileServiceWebClient") WebClient fileServiceWebClient) {
        this.fileServiceWebClient = fileServiceWebClient;
    }

    @Override
    public Mono<String> uploadFile(FilePart file) {
        final String uploadUrl = "/import";
        log.info("Sending request to file-service. Url: {}, filename: {}", uploadUrl, file.filename());

        return fileServiceWebClient.post()
            .uri(uploadUrl)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData("file", file))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
            .map(ApiResponse::getBody)
            .doOnSuccess(importCode -> log.info("File {} successfully uploaded. Import code: {}", file.filename(), importCode))
            .doOnError(error -> log.error("Error while uploading file: {}", file.filename(), error));
    }

    @Override
    public Mono<Void> deleteFileByImportCode(String importCode) {
        final String deleteUrl = "/delete/by-import-code/{import-code}";
        log.info("Sending request to file-service to delete file by import code. Url: {}, import code: {}", deleteUrl, importCode);

        return fileServiceWebClient.delete()
            .uri(deleteUrl, importCode)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
            .doOnError(error -> log.error("Error while deleting file: {}", importCode, error))
            .then();
    }

}
