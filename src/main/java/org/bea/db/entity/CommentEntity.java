package org.bea.db.entity;

import java.time.Instant;
import java.util.UUID;

public class CommentEntity {
    private UUID id;
    private UUID postId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
