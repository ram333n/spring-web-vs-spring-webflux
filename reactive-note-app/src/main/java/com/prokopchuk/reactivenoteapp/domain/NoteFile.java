package com.prokopchuk.reactivenoteapp.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "notes_files")
public class NoteFile {

    @Id
    private Long id;

    @Column("note_id")
    private Long noteId;

    @Column("import_code")
    private String importCode;

    @Column("file_prefix")
    private String filePrefix;

    @Column("file_extension")
    private String fileExtension;

}
