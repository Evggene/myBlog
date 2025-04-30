package org.bea.db.repository;

import org.bea.db.entity.PostEntity;
import org.bea.model.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository {

    List<PostEntity> findAll(int offset);

    long getCount();

    Post save(Post post);

    PostEntity getById(UUID id);
}
