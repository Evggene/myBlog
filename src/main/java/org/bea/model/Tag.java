package org.bea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
public record Tag (UUID id, String name) implements UUIDModel {
    @Override
    public void setId(UUID id) {

    }
}
