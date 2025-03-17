package com.prokopchuk.reactivenoteapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "notes")
public class Note extends AuditableEntity {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("title")
    private String title;

    @Column("content")
    private String content;

}
