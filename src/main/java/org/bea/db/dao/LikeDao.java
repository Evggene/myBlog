package org.bea.db.dao;

import org.bea.db.entity.LikeEntity;

import java.util.UUID;

public interface LikeDao {

    void createForPost(LikeEntity likeEntity);
    void increment(UUID postId);
    void decrement(UUID postId);
    LikeEntity findByPostId(UUID id);
}
