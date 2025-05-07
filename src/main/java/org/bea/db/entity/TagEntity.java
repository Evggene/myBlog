package org.bea.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity extends AuditFields implements UUIDEntity {
    private UUID id;
    private String name;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
