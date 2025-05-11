package org.bea.db.dao;

import org.bea.db.entity.TagEntity;

import java.util.UUID;

public interface TagDao {

    TagEntity setIdAndInsert(TagEntity tagEntity);
    TagEntity findByName(String name);
    TagEntity findById(UUID id);
}
