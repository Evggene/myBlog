package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.dao.TagsToPostDao;
import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;
import org.bea.db.entity.TagsToPostEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public abstract class CommonHandler {

    protected final TagDao tagDao;
    protected final ParagraphDao paragraphDao;
    protected final PostDao postDao;
    protected final LikeDao likeDao;
    protected final TagsToPostDao tagsToPostDao;

    /**
     * Обрабатывает строку, содержащую теги:
     * находит существующие и вставляет новые,
     * возвращает коллекцию объектов из бд
     */
    protected Collection<TagEntity> prepareTagsAndInsertNew(String tagsNameRawString) {
        if (tagsNameRawString.isBlank()) {
            return Collections.emptyList();
        }
        var tagsArray = split(tagsNameRawString);
        var tagsExisted = findExistedTags(tagsArray);
        var newTagsName = findNewTagsInRawString(tagsExisted, tagsArray);
        var tagsWithoutId = buildNewTags(newTagsName);
        var tagsCreated = insertNewTags(tagsWithoutId);
        addTagsExistedToTagsCreated(tagsCreated, tagsExisted);
        return tagsCreated;
    }

    private void addTagsExistedToTagsCreated(ArrayList<TagEntity> tagsCreated, Set<TagEntity> tagsExisted) {
        tagsCreated.addAll(tagsExisted);
    }

    private ArrayList<TagEntity> insertNewTags(Set<TagEntity> tagsWithoutId) {
        return new ArrayList<>(tagsWithoutId.stream()
                .map(tagDao::setIdAndInsert)
                .toList());
    }

    private Set<String> findNewTagsInRawString(Set<TagEntity> tagsExisted, String[] tags) {
        var tagStringCollection = tagsExisted.stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());
        var tagRawUniqCollection = Arrays.stream(tags)
                .collect(Collectors.toSet());
        tagRawUniqCollection.removeAll(tagStringCollection);
        return tagRawUniqCollection;
    }

    private Set<TagEntity> findExistedTags(String[] tags) {
        return Arrays.stream(tags)
                .map(tagDao::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private String[] split(String tagsRaw) {
        return tagsRaw.split(" ");
    }

    private Set<TagEntity> buildNewTags(Set<String> tagsUnique) {
        return tagsUnique.stream()
                .map(it -> TagEntity.builder().name(it).build())
                .collect(Collectors.toSet());
    }

    protected void buildLinkTagsToPostAndInsert(UUID id, Collection<TagEntity> tagsCreated) {
        var entities = tagsCreated.stream()
                .map(it -> buildTagsToPost(id, it))
                .collect(Collectors.toSet());
        entities.forEach(tagsToPostDao::createLinkTagToPost);
    }

    private TagsToPostEntity buildTagsToPost(UUID id, TagEntity it) {
        return TagsToPostEntity.builder().postId(id).tagId(it.getId()).build();
    }

    protected void buildParagraphsAndInsert(String[] paragraphs, UUID postId) {
        var entities = IntStream.range(0, paragraphs.length)
                .mapToObj(i -> buildParagraphs(paragraphs, postId, i))
                .toList();
        entities.forEach(paragraphDao::setIdAndInsert);
    }

    private ParagraphEntity buildParagraphs(String[] paragraphs, UUID postId, int i) {
        return ParagraphEntity.builder()
                .ord(i + 1)
                .postId(postId)
                .text(paragraphs[i].strip())
                .build();
    }

}
