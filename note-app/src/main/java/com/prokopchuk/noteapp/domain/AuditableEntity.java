package com.prokopchuk.noteapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@MappedSuperclass
public abstract class AuditableEntity {

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedBy
    @Column(name = "date_modified", nullable = false)
    private LocalDateTime dateModified;

}
