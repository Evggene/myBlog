package org.bea.db.repository;

import org.bea.db.entity.TagEntity;
import org.bea.model.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository {

    /**
     * Находит все посты для списка без списка комментариев и параграфов
     */
    List<Post> findAllPreviewMode(int offset, int limit);

    /**
     * Возвращает пост по идентификатору со всеми связанными объектами
     */
    Post findByIdFullMode(UUID id);
    /**
     * Находит все посты по списку тегов для списка без списка комментариев и параграфов
     */
    List<Post> findByTagPreviewMode(List<TagEntity> tags, int offset, int limit);
}
