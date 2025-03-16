package com.prokopchuk.commons.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class NoteDto {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private List<NoteFileDto> attachedFiles;

}
