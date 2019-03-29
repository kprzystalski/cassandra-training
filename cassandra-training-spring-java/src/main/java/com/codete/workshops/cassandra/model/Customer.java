package com.codete.workshops.cassandra.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Customer {

    @PrimaryKey
    private UUID id;

    @Column
    private String fullName;

    @Column
    private String email;

}
