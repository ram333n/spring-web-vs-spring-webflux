package com.prokopchuk.fileservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
    name = "file_data",
    uniqueConstraints = {
        @UniqueConstraint(name = "un$file_data$import_code", columnNames = {"import_code"})
    },
    indexes = {
        @Index(name = "in$file_data$import_code", columnList = "import_code")
    }
)
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "import_code", nullable = false)
    private String importCode;

    @Column(name = "path", nullable = false)
    private String path;

}
