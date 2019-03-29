package com.codete.workshops.cassandra.model;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Product {

    @PrimaryKeyColumn(name = "id")
    private UUID id;

    @PrimaryKeyColumn(name = "barcode", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String barcode;

    @Column
    private String name;

}
