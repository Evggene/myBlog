package org.bea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.rmi.server.UID;
import java.util.UUID;

@Builder
public record TagsToPost (UUID postId, UUID tagId) {}
