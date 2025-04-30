package org.bea.db.dao;

import org.bea.model.Tag;

public interface TagRepository {

    Tag setIdAndInsert(Tag tag);
}
