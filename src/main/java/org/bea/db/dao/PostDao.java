package org.bea.db.dao;

import org.bea.db.entity.PostEntity;

import java.util.UUID;

public interface PostDao {

    long getCount();

    PostEntity setIdAndInsert(PostEntity postEntity);

    PostEntity findById(UUID id);

    void update(PostEntity postEntity);

    void delete(UUID id);
}
