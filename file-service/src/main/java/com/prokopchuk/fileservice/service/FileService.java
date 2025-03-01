package com.prokopchuk.fileservice.service;

import java.io.InputStream;

public interface FileService {

    String importFile(InputStream stream, String fileName);

    void deleteFileByImportCode(String importCode);
}
