package org.bea.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bea.model.Tag;

import java.time.Instant;
import java.util.List;
import java.util.Set;
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
    private int likesCount;
    private String[] tags;
}
