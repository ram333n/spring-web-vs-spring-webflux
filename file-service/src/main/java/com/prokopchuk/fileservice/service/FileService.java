package com.prokopchuk.fileservice.service;

import com.prokopchuk.fileservice.common.dto.FileDownloadDto;
import java.io.File;
import java.io.InputStream;

public interface FileService {

    String importFile(InputStream stream, String fileName);

    void deleteFileByImportCode(String importCode);

    FileDownloadDto getFileByImportCode(String importCode);

}
