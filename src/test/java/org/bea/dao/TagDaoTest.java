package org.bea.dao;

import org.bea.db.entity.Tag;
import org.bea.db.entity.TagsToPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TagDaoTest extends CommonDaoContext {

    private final UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");
    private final UUID tagId1 = UUID.fromString("50000000-0000-0000-0000-000000000001");
    private final String tagName1 = "technology";
    private final String tagName2 = "science";

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM tags_to_post");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM posts");
        
        jdbcTemplate.execute(
                """
                INSERT INTO posts(id, title, text_preview, image_path) 
                    VALUES
                ('20000000-0000-0000-0000-000000000001', 
                'Test Post', 
                'Test Preview', 
                'test.png');
                """);
    }

    @Test
    void setIdAndInsertTest() {
        var newTag = createTagWithoutId();
        tagDao.setIdAndInsert(newTag);

        var savedTag = tagDao.findByName(tagName1);

        assertEquals(newTag.getName(), savedTag.getName());
    }

    @Test
    void insertTagsToPostTest() {
        var newTag = createTagWithoutId();
        var tagSaved = tagDao.setIdAndInsert(newTag);

        var newLink = TagsToPost.builder().postId(postId).tagId(tagSaved.getId()).build();
        tagDao.createLinkTagToPost(newLink);

        var post = postAggregateRepository.findById(postId);
        assertEquals(1, post.getTags().length);
        assertEquals(tagName1, post.getTags()[0]);
    }

    @Test
    void deleteLinkTagsToPostTest() {
        var firstTag = createTagWithoutId();
        var firstTagSaved = tagDao.setIdAndInsert(firstTag);

        var firstLink = TagsToPost.builder().postId(postId).tagId(firstTagSaved.getId()).build();
        tagDao.createLinkTagToPost(firstLink);

        var post = postAggregateRepository.findById(postId);
        assertEquals(1, post.getTags().length);
        assertEquals(tagName1, post.getTags()[0]);

        tagDao.deleteLinkTagsToPost(postId, "post_id");

        var postWithoutTag = postAggregateRepository.findById(postId);
        assertNull(postWithoutTag.getTags()[0]);
    }

    @Test
    void countPostsByTagTest() {
        var newTag = createTagWithoutId();
        var tagSaved = tagDao.setIdAndInsert(newTag);

        var newLink = TagsToPost.builder().postId(postId).tagId(tagSaved.getId()).build();
        tagDao.createLinkTagToPost(newLink);

        var post = postAggregateRepository.findById(postId);
        assertEquals(1, post.getTags().length);

        var count = tagDao.countPostsByTags(List.of(tagSaved));

        assertEquals(1, count);
    }

    @Test
    void countPostsByTagsTest() {
        var firstTag = createTagWithoutId();
        var firstTagSaved = tagDao.setIdAndInsert(firstTag);

        var secondTag = Tag.builder().name(tagName2).build();
        var secondTagSaved = tagDao.setIdAndInsert(secondTag);

        var firstLink = TagsToPost.builder().postId(postId).tagId(firstTagSaved.getId()).build();
        tagDao.createLinkTagToPost(firstLink);

        var secondLink = TagsToPost.builder().postId(postId).tagId(secondTagSaved.getId()).build();
        tagDao.createLinkTagToPost(secondLink);

        var post = postAggregateRepository.findById(postId);
        assertEquals(2, post.getTags().length);

        var count = tagDao.countPostsByTags(List.of(firstTagSaved, secondTagSaved));

        assertEquals(1, count);
    }

    private Tag createTagWithoutId() {
        return Tag.builder().name(tagName1).build();
    }
}
