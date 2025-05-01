package org.bea.db.repository;

import org.bea.db.entity.PostAggregate;

import java.util.List;
import java.util.UUID;

public interface PostAggregateRepository {

    List<PostAggregate> findAll(int offset);
    PostAggregate findById(UUID id);

    void delete(UUID id);
}
