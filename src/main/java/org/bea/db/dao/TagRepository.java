package org.bea.db.dao;

import org.bea.model.Tag;
import org.bea.model.TagsToPost;

public interface TagRepository {

    Tag setIdAndInsert(Tag tag);

    void insert(TagsToPost tagsToPost);
}
