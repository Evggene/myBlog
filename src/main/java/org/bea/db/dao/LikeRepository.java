package org.bea.db.dao;

import org.bea.model.Like;
import org.bea.model.Post;
import org.bea.service.LikeHandler;

import java.util.List;
import java.util.UUID;

public interface LikeRepository {

    void createForPost(Like like);
    void incDec(UUID postId, LikeHandler.LikeActionType actionType);
}
