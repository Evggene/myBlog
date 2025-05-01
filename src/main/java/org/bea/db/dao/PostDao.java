package org.bea.db.dao;

import org.bea.db.entity.PostAggregate;
import org.bea.model.Post;

import java.util.UUID;

public interface PostDao {

    long getCount();

    Post setIdAndInsert(Post post);

    PostAggregate getById(UUID id);

    Post findPostById(UUID id);

    void update(Post post);

    void delete(UUID id);
}
