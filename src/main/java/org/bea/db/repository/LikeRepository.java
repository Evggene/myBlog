package org.bea.db.repository;

import org.bea.model.Like;
import org.bea.model.Post;

import java.util.List;
import java.util.UUID;

public interface LikeRepository {

    void createForPost(Like like);
}
