package com.prokopchuk.commons.dto;

import lombok.Data;

@Data
public class NoteFileDto {

    private Long id;
    private Long noteId;
    private String importCode;
    private String filePrefix;
    private String fileExtension;

}
