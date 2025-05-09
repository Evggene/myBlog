package org.bea.service;

import org.bea.config.CommonServiceTest;
import org.bea.db.entity.CommentEntity;
import org.bea.db.entity.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class DeletePostHandlerTest extends CommonServiceTest {

    // поля title и text не могут быть пустыми (валидация в контроллере)

    @BeforeEach
    void truncateAllTable() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM likes");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM tags_to_post");
        jdbcTemplate.execute("DELETE FROM paragraphs");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    @Test
    void deletePost_success() {
        var postId = createPost();
        deletePostHandler.deletePost(postId);

        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(0, postsPreviewMode.size());

        var post = postDao.findById(postId);
        org.junit.jupiter.api.Assertions.assertNull(post);

        var aPost = postRepository.findByIdFullMode(postId);
        org.junit.jupiter.api.Assertions.assertNull(aPost);

        var tag1 = TagEntity.builder().id(UUID.randomUUID()).name("123").build();
        var bPost = postRepository.findByTagPreviewMode(List.of(tag1), 0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(0, bPost.size());
    }

    private UUID createPost() {
        addPostHandler.addPost("test", "test", "123 456", "");

        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());

        var comment = CommentEntity.builder().postId(postsPreviewMode.get(0).getId()).content("test comment").build();
        commentDao.setIdAndInsert(comment);

        likeDao.increment(postsPreviewMode.get(0).getId());

        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        return postFullMode.getId();
    }
}
