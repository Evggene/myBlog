package org.bea.db.dao;

import org.bea.db.entity.CommentEntity;
import org.bea.db.entity.PostEntity;

import java.util.List;
import java.util.UUID;

public interface CommentDao {

    void update(CommentEntity commentEntity);
    void delete(UUID id, String byColumn);
    CommentEntity setIdAndInsert(CommentEntity commentEntity);

    CommentEntity findById(UUID id);

    List<CommentEntity> findListById(UUID id);
}
