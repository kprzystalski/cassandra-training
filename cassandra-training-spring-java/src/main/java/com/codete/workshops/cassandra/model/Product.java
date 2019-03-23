package com.codete.workshops.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
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

    @Column
    private BigDecimal price;

}
