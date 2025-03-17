package com.prokopchuk.reactivenoteapp.domain;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

@Data
public abstract class AuditableEntity {

    @CreatedDate
    @Column("date_created")
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column("date_modified")
    private LocalDateTime dateModified;

}
