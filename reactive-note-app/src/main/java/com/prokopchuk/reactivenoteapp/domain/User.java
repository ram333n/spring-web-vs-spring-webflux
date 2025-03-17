package com.prokopchuk.reactivenoteapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users")
public class User {

    @Id
    private Long id;

    @Column("name")
    private String name;

}
