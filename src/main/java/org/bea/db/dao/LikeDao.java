package org.bea.db.dao;

import org.bea.db.entity.LikeRecord;

import java.util.UUID;

public interface LikeDao {

    void createForPost(LikeRecord likeRecord);
    void increment(UUID postId);
    void decrement(UUID postId);
}
