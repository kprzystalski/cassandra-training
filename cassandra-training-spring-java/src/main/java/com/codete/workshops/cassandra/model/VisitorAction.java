package com.codete.workshops.cassandra.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table
public class VisitorAction {

    @PrimaryKey
    private UUID id;

    @Column
    private UUID visitorId;

    private ActionEvent event;

    @Column
    private LocalDateTime timestamp;

}
