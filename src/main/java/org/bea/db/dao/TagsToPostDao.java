package org.bea.db.dao;

import org.bea.db.entity.TagEntity;
import org.bea.db.entity.TagsToPostEntity;

import java.util.List;
import java.util.UUID;

public interface TagsToPostDao {

    TagsToPostEntity createLinkTagToPost(TagsToPostEntity tagsToPostEntity);
    void deleteLinkTagsToPost(UUID id, String byColumn);
    long countPostsByTags(List<TagEntity> tagEntities);
    TagsToPostEntity findById(UUID id);
}
