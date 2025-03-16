package com.prokopchuk.noteapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file);

    void deleteFileByImportCode(String importCode);

}
