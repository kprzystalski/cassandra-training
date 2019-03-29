package com.codete.workshops.cassandra.model;

import com.codete.workshops.cassandra.model.types.Gender;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Visitor {

    @PrimaryKey
    private UUID id;

    @Column
    private String type;

    @Column
    private UUID existingCustomerId;

    @Column
    private int age;

    @Column
    private String ip;

    @Column
    private String location;

    @Column
    private String profession;

    @Column
    private Gender gender;

    @Column
    private Set<String> interests;

    @Column
    private Map<String, String> additional;

    @Column
    private LocalDateTime timestamp;

}
