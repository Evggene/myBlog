package org.bea.db.dao;

import org.bea.model.Tag;
import org.bea.model.TagsToPost;

import java.util.UUID;

public interface TagRepository {

    Tag setIdAndInsert(Tag tag);

    void insert(TagsToPost tagsToPost);

    void deleteLinkTagsToPost(UUID id);
}
