package org.bea.db.entity;

import lombok.Builder;

import java.util.UUID;

@Builder
public record LikeRecord(UUID postId, int likesCount) {}

