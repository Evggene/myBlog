package org.bea.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagsToPostEntity extends AuditFields implements UUIDEntity {
    private UUID id;
    private UUID postId;
    private UUID tagId;
}
