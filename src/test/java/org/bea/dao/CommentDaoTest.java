package org.bea.dao;

import org.bea.db.entity.CommentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommentDaoTest extends CommonDaoTest {

    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM comments");
    }

    @Test
    void setIdAndInsertTest() {
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        var commentFound = commentDao.findById(commentSaved.getId());

        assertEquals(commentFound, commentSaved);
        Assertions.assertNotNull(commentFound.getCreatedAt());
        Assertions.assertNull(commentFound.getUpdatedAt());
        Assertions.assertNull(commentFound.getDeletedAt());
    }

    @Test
    void updateTest() {
        var newContent = "Updated comment content";
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        var commentFound = commentDao.findById(commentSaved.getId());
        assertEquals(commentSaved, commentFound);

        commentSaved.setContent(newContent);
        commentDao.update(commentSaved);

        var commentUpdatedFound = commentDao.findById(commentSaved.getId());

        assertEquals(commentUpdatedFound.getContent(), newContent);
        assertEquals(commentUpdatedFound.getPostId(), comment.getPostId());
        assertNotNull(commentUpdatedFound.getUpdatedAt());
        assertNotNull(commentUpdatedFound.getCreatedAt());
        assertNull(commentUpdatedFound.getDeletedAt());
    }

    @Test
    void deleteByIdTest() {
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        commentDao.delete(commentSaved.getId(), "id");

        var commentDeleted = commentDao.findById(commentSaved.getId());
        assertNull(commentDeleted);
    }

    @Test
    void deleteByPostIdTest() {
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        commentDao.delete(commentSaved.getPostId(), "post_id");

        var commentDeleted = commentDao.findListById(commentSaved.getPostId());
        assertEquals(0, commentDeleted.size());
    }

    private CommentEntity createCommentWithoutId() {
        return CommentEntity.builder()
                .postId(postId)
                .content("Test comment content")
                .build();
    }
}
