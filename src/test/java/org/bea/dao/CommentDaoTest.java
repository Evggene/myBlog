package org.bea.dao;

import org.bea.db.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentDaoTest extends CommonDaoContext {

    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM comments");
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
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        var postWithComment = postAggregateRepository.findById(postId);

        assertEquals(commentSaved.getContent(), postWithComment.getComments().get(0).getContent());
        assertEquals(1, postWithComment.getComments().size());
    }

    @Test
    void updateTest() {
        var newContent = "Updated comment content";
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        var postWithComment = postAggregateRepository.findById(postId);
        assertEquals(commentSaved.getContent(), postWithComment.getComments().get(0).getContent());
        assertEquals(1, postWithComment.getComments().size());

        commentSaved.setContent(newContent);
        commentDao.update(commentSaved);

        var postWithNewComment = postAggregateRepository.findById(postId);
        assertEquals(commentSaved.getContent(), postWithNewComment.getComments().get(0).getContent());
        assertEquals(1, postWithNewComment.getComments().size());
    }

    @Test
    void deleteByIdTest() {
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        commentDao.delete(commentSaved.getId(), "id");

        var postWithComment = postAggregateRepository.findById(postId);
        assertEquals(0, postWithComment.getComments().size());
    }

    @Test
    void deleteByPostIdTest() {
        var comment = createCommentWithoutId();
        var commentSaved = commentDao.setIdAndInsert(comment);

        commentDao.delete(commentSaved.getPostId(), "post_id");

        var postWithComment = postAggregateRepository.findById(postId);
        assertEquals(0, postWithComment.getComments().size());
    }

    private Comment createCommentWithoutId() {
        return Comment.builder().postId(postId).content("Test comment content").build();
    }
}
