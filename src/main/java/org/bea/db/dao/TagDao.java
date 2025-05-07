package org.bea.db.dao;

import org.bea.db.entity.TagEntity;
import org.bea.db.entity.TagsToPostLink;

import java.util.List;
import java.util.UUID;

public interface TagDao {

    TagEntity setIdAndInsert(TagEntity tagEntity);

    void createLinkTagToPost(TagsToPostLink tagsToPostLink);

    void deleteLinkTagsToPost(UUID id, String byColumn);

    TagEntity findByName(String name);

    long countPostsByTags(List<TagEntity> tagEntities);
}
