package com.codete.workshops.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Order {

    @PrimaryKey
    private UUID id;

    private UUID customerId;

    private UUID shopId;

    private UUID productID;

    private BigDecimal singleProductPrice;

    private int quantity;

    @Column
    private LocalDateTime timestamp;

}
