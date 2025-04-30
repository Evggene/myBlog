package org.bea.db.repository;

import org.bea.db.entity.PostAggregate;
import org.bea.model.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository {

    List<PostAggregate> findAll(int offset);
    List<Post> findAll();

    long getCount();

    Post setIdAndInsert(Post post);

    PostAggregate getById(UUID id);

    Post findPostById(UUID id);
}
