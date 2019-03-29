package com.codete.workshops.cassandra.model;

import com.codete.workshops.cassandra.model.types.EventTarget;
import com.codete.workshops.cassandra.model.types.EventType;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@UserDefinedType
public class ActionEvent {

    @Column
    private EventType eventType;

    @Column
    private EventTarget eventTarget;

    @Column
    private Map<String, String> additional;

}
