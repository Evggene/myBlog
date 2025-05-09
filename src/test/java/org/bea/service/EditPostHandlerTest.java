package org.bea.service;

import org.assertj.core.api.Assertions;
import org.bea.config.CommonServiceTest;
import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;
import org.bea.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

public class EditPostHandlerTest extends CommonServiceTest {

    @Sql(scripts = "classpath:scripts/init_posts.sql")
    @Test
    void editPostTest() {
        var post = postRepository.findByIdFullMode(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));

        editPostHandler.editPost(
                post.getId(),
                "new title",
                "new text",
                "new tag",
                "");
        var postEdited = postRepository.findByIdFullMode(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));

        var tag1 = TagEntity.builder().id(UUID.randomUUID()).name("new").build();
        var tag2 = TagEntity.builder().id(UUID.randomUUID()).name("tag").build();
        var par1 = ParagraphEntity.builder().id(UUID.randomUUID()).text("new text").ord(1).postId(postEdited.getId()).build();

        var expected = Post.builder()
                .id(post.getId())
                .textPreview("new text")
                .tags(List.of(tag1, tag2))
                .likesCount(89)
                .title("new title")
                .textParts(List.of(par1))
                .comments(post.getComments())
                .imagePath("")
                .build();
        Assertions.assertThat(postEdited)
                .usingRecursiveComparison()
                .ignoringFields("imagePath", "id", "tags.id", "textParts.id")
                .isEqualTo(expected);
    }

}
