package org.bea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements UUIDModel{
    private UUID id;
    private String name;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
