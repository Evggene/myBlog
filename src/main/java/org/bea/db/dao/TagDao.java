package org.bea.db.dao;

import org.bea.db.entity.Tag;
import org.bea.db.entity.TagsToPost;

import java.util.List;
import java.util.UUID;

public interface TagDao {

    Tag setIdAndInsert(Tag tag);

    void createLinkTagToPost(TagsToPost tagsToPost);

    void deleteLinkTagsToPost(UUID id, String byColumn);

    Tag findByName(String name);

    long countPostsByTags(List<Tag> tags);
}
