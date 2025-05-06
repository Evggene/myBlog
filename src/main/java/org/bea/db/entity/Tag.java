package org.bea.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AuditFields implements UUIDModel{
    private UUID id;
    private String name;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
