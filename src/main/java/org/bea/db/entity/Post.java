package org.bea.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AuditFields implements UUIDModel{
    private UUID id;
    private String title;
    private String imagePath;
    private String textPreview;
}
