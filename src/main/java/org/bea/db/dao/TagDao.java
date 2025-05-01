package org.bea.db.dao;

import org.bea.model.Tag;
import org.bea.model.TagsToPost;

import java.util.List;
import java.util.UUID;

public interface TagDao {

    Tag setIdAndInsert(Tag tag);

    void insert(TagsToPost tagsToPost);

    void deleteLinkTagsToPost(UUID id);

    Tag findByName(String name);

    long countPostsByTags(List<Tag> tags);
}
