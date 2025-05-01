package org.bea.db.repository;

import org.bea.db.entity.PostAggregate;
import org.bea.model.Tag;

import java.util.List;
import java.util.UUID;

public interface PostAggregateRepository {

    List<PostAggregate> findAll(int offset, int limit);
    PostAggregate findById(UUID id);
    List<PostAggregate> findByTag(List<Tag> tags, int offset, int limit);
}
