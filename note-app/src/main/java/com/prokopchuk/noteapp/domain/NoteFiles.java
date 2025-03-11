package com.prokopchuk.noteapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
    name = "notes_files",
    uniqueConstraints = {
        @UniqueConstraint(name = "un$notes_files$import_code", columnNames = {"import_code"}),
        @UniqueConstraint(name = "un$notes_files$note_id$file_prefix$file_extension", columnNames = {"import_code", "file_prefix", "file_extension"})
    }
)
public class NoteFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Note.class)
    @JoinColumn(name = "note_id")
    private Long noteId;

    @Column(name = "import_code")
    private String importCode;

    @Column(name = "file_prefix")
    private String filePrefix;

    @Column(name = "file_extension")
    private String fileExtension;

}
