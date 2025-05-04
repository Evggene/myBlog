package org.bea.service;

import org.assertj.core.api.Assertions;
import org.bea.model.PostAggregate;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.UUID;

public class EditPostHandlerTest extends CommonServiceContext{

    @Sql(scripts = "classpath:scripts/init_posts.sql")
    @Test
    void editPostTest() {
        var post = postAggregateRepository.findByIdFullMode(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));

        editPostHandler.editPost(
                post.getId(),
                "new title",
                "new text",
                "new tag",
                "");
        var postEdited = postAggregateRepository.findByIdFullMode(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));

        var expected = PostAggregate.builder()
                .id(post.getId())
                .textPreview("new text")
                .tags(new String[]{"new", "tag"})
                .likesCount(89)
                .title("new title")
                .textParts(new String[]{"new text"})
                .comments(post.getComments())
                .imagePath("")
                .build();
        Assertions.assertThat(postEdited)
                .usingRecursiveComparison()
                .ignoringFields("imagePath")
                .isEqualTo(expected);
    }

}
