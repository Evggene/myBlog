package org.bea.db.repository;

import org.bea.model.Post;
import org.bea.model.Tag;

import java.util.List;
import java.util.UUID;

public interface TagRepository {

    Tag save(Tag tag);
}
