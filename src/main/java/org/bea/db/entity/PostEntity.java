package org.bea.db.entity;

import java.time.Instant;
import java.util.UUID;

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
