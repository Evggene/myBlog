package org.bea.db.dao;

import org.bea.model.Like;
import org.bea.service.LikeActionHandler;

import java.util.UUID;

public interface LikeRepository {

    void createForPost(Like like);
    void incDec(UUID postId, LikeActionHandler.LikeActionType actionType);
}
