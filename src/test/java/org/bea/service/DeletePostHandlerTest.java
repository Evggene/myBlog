package org.bea.service;

import org.assertj.core.api.Assertions;
import org.bea.db.entity.Comment;
import org.bea.model.PostAggregate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class DeletePostHandlerTest extends CommonServiceContext {

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
        var postId = createPostAndCheck();
        deletePostHandler.deletePost(postId);

        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(0, postsPreviewMode.size());

        var post = postDao.findById(postId);
        org.junit.jupiter.api.Assertions.assertNull(post);
    }

    private UUID createPostAndCheck() {
        addPostHandler.addPost("test", "test", "123 456", "");

        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());

        var comment = Comment.builder().postId(postsPreviewMode.get(0).getId()).content("test comment").build();
        commentDao.setIdAndInsert(comment);

        likeDao.incDec(postsPreviewMode.get(0).getId(), LikeActionHandler.LikeActionType.INCREMENT);

        var postFullMode = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = PostAggregate.builder()
                .title("test")
                .textPreview("test")
                .textParts(new String[]{"test"})
                .likesCount(1)
                .imagePath("")
                .tags(new String[]{"123", "456"})
                .comments(List.of(comment))
                .build();

        Assertions.assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);

        return postFullMode.getId();
    }
}
