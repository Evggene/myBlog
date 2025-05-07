package org.bea.dao;

import org.bea.db.entity.PostEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PostEntityDaoTest extends CommonDaoTest {

    private final static UUID id = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute(
            """
            INSERT INTO posts(id, title, text_preview, image_path) 
                VALUES
            ('20000000-0000-0000-0000-000000000001', 
            '1 The Future of AI', 
            'How AI is changing the world', 
            'Снимок экрана от 2024-08-29 13-21-40.png');
            """);
    }

    @Test
    void setIdAndInsertTest() {
        var post = createPostWithoutId();
        var postSaved = postDao.setIdAndInsert(post);
        var postInDb = postDao.findById(postSaved.getId());
        Assertions.assertNotNull(postInDb);
        Assertions.assertEquals(postSaved, postInDb);
        Assertions.assertNotNull(postInDb.getCreatedAt());
        Assertions.assertNull(postInDb.getUpdatedAt());
        Assertions.assertNull(postInDb.getDeletedAt());
    }

    @Test
    void countTest() {
        var one = postDao.getCount();
        Assertions.assertEquals(1, one);

        var post = createPostWithoutId();
        postDao.setIdAndInsert(post);
        var two = postDao.getCount();

        Assertions.assertEquals(2, two);
    }

    @Test
    void findByIdTest() {
       var post = postDao.findById(id);
       Assertions.assertNotNull(post);
       assertEquals("1 The Future of AI", post.getTitle());
       assertEquals("Снимок экрана от 2024-08-29 13-21-40.png", post.getImagePath());
       assertEquals("How AI is changing the world", post.getTextPreview());
    }

    @Test
    void updateTest() {
        var title = "new title";
        var preview = "new preview";
        var postRaw = createPostWithoutId();

        var post = postDao.setIdAndInsert(postRaw);
        post.setTitle("new title");
        post.setTextPreview("new preview");

        postDao.update(post);
        var postEdited = postDao.findById(post.getId());

        assertEquals(title, postEdited.getTitle());
        assertEquals(preview, post.getTextPreview());
        assertNotNull(postEdited.getUpdatedAt());
        assertNotNull(postEdited.getCreatedAt());
        assertNull(postEdited.getDeletedAt());
    }

    @Test
    void delete() {
        var post = postDao.findById(id);
        Assertions.assertNotNull(post);
        postDao.delete(id);

        var deleted = postDao.findById(id);
        Assertions.assertNull(deleted);
    }

    private PostEntity createPostWithoutId() {
        var post = new PostEntity();
        post.setTitle("random title");
        post.setTextPreview("random preview");
        post.setImagePath("");
        return post;
    }

}
