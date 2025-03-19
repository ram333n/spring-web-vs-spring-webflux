package com.prokopchuk.reactivenoteapp.service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface FileService {

    Mono<String> uploadFile(FilePart file);

    Mono<Void> deleteFileByImportCode(String importCode);

}
