package com.prokopchuk.noteapp.service.impl;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.exception.OperationException;
import com.prokopchuk.noteapp.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
public class DefaultFileService implements FileService {

    private final RestTemplate fileServiceRestTemplate;

    public DefaultFileService(@Qualifier("fileServiceRestTemplate") RestTemplate fileServiceRestTemplate) {
        this.fileServiceRestTemplate = fileServiceRestTemplate;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        MultiValueMap<String, Object> body = initUploadFileBody(file);
        HttpHeaders headers = initUploadFileHeaders();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        final String uploadUrl = "/import";
        log.info("Sending request to file-service. Url: {}", uploadUrl);

        ResponseEntity<ApiResponse<String>> response
            = fileServiceRestTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new OperationException(String.format(
                "Failed to upload file on url %s. Response code: %s, message: %s",
                uploadUrl,
                response.getStatusCode(),
                response.getBody().getMessage()
            ));
        }

        return response.getBody().getBody();
    }

    private MultiValueMap<String, Object> initUploadFileBody(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        return body;
    }

    private HttpHeaders initUploadFileHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return headers;
    }

}
