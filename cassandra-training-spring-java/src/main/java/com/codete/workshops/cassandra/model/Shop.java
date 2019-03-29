package com.codete.workshops.cassandra.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Shop {

    @PrimaryKey
    private UUID id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private Map<UUID, Integer> availableProductsQuantity;

}
