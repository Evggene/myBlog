package org.bea.db.repository;

import org.bea.model.Post;

import java.util.List;
import java.util.UUID;

public interface TagRepository {

    List<Post> findAll(int offset);

    long getCount();

    void save(Post post);

    Post getById(UUID id);
}
