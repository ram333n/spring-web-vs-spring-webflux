package com.prokopchuk.commons.dto;

import lombok.Data;

@Data
public class NoteDto {

    private Long id;
    private Long userId;
    private String title;
    private String content;

}
