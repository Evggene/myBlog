package org.bea.repository;

import org.bea.model.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository {

    List<Post> findAll(int offset);

    long getCount();

    void save(Post post);

    Post getById(UUID id);
}
