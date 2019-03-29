package com.codete.workshops.cassandra.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Order {

    @PrimaryKey
    private UUID id;

    @Column
    private UUID customerId;

    @Column
    private UUID shopId;

    @Column
    private UUID productID;

    @Column
    private BigDecimal unitPrice;

    @Column
    private int quantity;

    @Column
    private LocalDateTime timestamp;

}
