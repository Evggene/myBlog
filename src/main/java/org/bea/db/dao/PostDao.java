package org.bea.db.dao;

import org.bea.db.entity.Post;

import java.util.UUID;

public interface PostDao {

    long getCount();

    Post setIdAndInsert(Post post);

    Post findById(UUID id);

    void update(Post post);

    void delete(UUID id);
}
