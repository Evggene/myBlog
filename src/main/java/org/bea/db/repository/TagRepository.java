package org.bea.db.repository;

import org.bea.model.Tag;

public interface TagRepository {

    Tag setIdAndInsert(Tag tag);
}
