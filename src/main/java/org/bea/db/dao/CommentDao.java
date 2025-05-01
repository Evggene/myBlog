package org.bea.db.dao;

import org.bea.model.Comment;

import java.util.UUID;

public interface CommentDao {

    void update(Comment comment);
    void delete(UUID id, String byColumn);
    Comment setIdAndInsert(Comment comment);
}
