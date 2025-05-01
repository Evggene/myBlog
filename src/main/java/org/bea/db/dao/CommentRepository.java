package org.bea.db.dao;

import org.bea.model.Comment;

import java.util.UUID;

public interface CommentRepository {

    void update(Comment comment);
    void delete(UUID id, String byColumn);
    Comment setIdAndInsert(Comment comment);
}
