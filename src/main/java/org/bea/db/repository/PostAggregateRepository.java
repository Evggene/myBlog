package org.bea.db.repository;

import org.bea.model.PostAggregate;
import org.bea.db.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface PostAggregateRepository {

    /**
     * Находит все посты для списка без списка комментариев и параграфов
     */
    List<PostAggregate> findAllPreviewMode(int offset, int limit);

    /**
     * Возвращает пост по идентификатору со всеми связанными объектами
     */
    PostAggregate findByIdFullMode(UUID id);
    /**
     * Находит все посты по списку тегов для списка без списка комментариев и параграфов
     */
    List<PostAggregate> findByTagPreviewMode(List<Tag> tags, int offset, int limit);
}
