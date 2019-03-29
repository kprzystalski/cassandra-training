package com.codete.workshops.cassandra.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Map;

@UserDefinedType
public class ActionEvent {

    @Column
    private String eventType;

    @Column
    private String eventTarget;

    @Column
    private Map<String, String> additional;

}
