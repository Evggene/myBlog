package org.bea.db.dao;

import org.bea.db.entity.Like;
import org.bea.service.LikeActionHandler;

import java.util.UUID;

public interface LikeDao {

    void createForPost(Like like);
    void incDec(UUID postId, LikeActionHandler.LikeActionType actionType);
}
