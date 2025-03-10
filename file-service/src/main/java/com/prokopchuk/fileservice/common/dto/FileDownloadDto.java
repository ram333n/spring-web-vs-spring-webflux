package com.prokopchuk.fileservice.common.dto;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class FileDownloadDto {

    private String fileName;
    private String fileExtension;
    private File file;

    public String getFullFileName() {
        return fileName + "." + fileExtension;
    }

}
