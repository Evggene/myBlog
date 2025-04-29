package org.bea.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    private UUID id;
    private String title;
    private String imagePath;
    private String content;
    private String textPreview;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
