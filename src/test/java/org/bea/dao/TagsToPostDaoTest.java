package org.bea.dao;

import org.bea.db.entity.TagEntity;
import org.bea.db.entity.TagsToPostEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TagsToPostDaoTest extends CommonDaoTest {

    private final UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");
    private final UUID tagId1 = UUID.fromString("50000000-0000-0000-0000-000000000001");
    private final UUID tagId2 = UUID.fromString("50000000-0000-0000-0000-000000000002");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM tags_to_post");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM posts");
    }

    @Test
    void createLinkTagToPostTest() {
        var link = createTagsToPostLinkWithoutId();
        var linkSaved = tagsToPostDao.createLinkTagToPost(link);

        var linkFound = tagsToPostDao.findById(linkSaved.getId());

        assertEquals(linkSaved.getPostId(), linkFound.getPostId());
        assertEquals(linkSaved.getTagId(), linkFound.getTagId());
        Assertions.assertNotNull(linkFound.getCreatedAt());
        Assertions.assertNull(linkFound.getUpdatedAt());
        Assertions.assertNull(linkFound.getDeletedAt());
    }

    @Test
    void deleteLinkTagsToPostTest() {
        var firstLink = createTagsToPostLinkWithoutId();
        var firstLInkSaved = tagsToPostDao.createLinkTagToPost(firstLink);

        tagsToPostDao.deleteLinkTagsToPost(postId, "post_id");

        var postWithoutTag = tagsToPostDao.findById(firstLInkSaved.getId());
        assertNull(postWithoutTag);
    }

    @Test
    void countPostsByTagTest() {
        var firstTag = createTagsToPostLinkWithoutId();
        tagsToPostDao.createLinkTagToPost(firstTag);

        var tag = TagEntity.builder().name("test").id(tagId1).build();
        var count = tagsToPostDao.countPostsByTags(List.of(tag));

        assertEquals(1, count);
    }

    @Test
    void countPostsByTagsTest() {
        var firstLink = TagsToPostEntity.builder().postId(postId).tagId(tagId1).build();
        tagsToPostDao.createLinkTagToPost(firstLink);

        var secondLink = TagsToPostEntity.builder().postId(postId).tagId(tagId1).build();
        tagsToPostDao.createLinkTagToPost(secondLink);

        var tag1 = TagEntity.builder().id(tagId1).name("test1").build();
        var tag2 = TagEntity.builder().id(tagId2).name("test1").build();

        var count = tagsToPostDao.countPostsByTags(List.of(tag1, tag2));

        assertEquals(1, count);
    }

    private TagsToPostEntity createTagsToPostLinkWithoutId() {
        return TagsToPostEntity.builder().postId(postId).tagId(tagId1).build();
    }
}
