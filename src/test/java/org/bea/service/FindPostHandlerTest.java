package org.bea.service;

import org.bea.config.CommonServiceTest;
import org.bea.dto.PageOfPostsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

public class FindPostHandlerTest extends CommonServiceTest {

    @Sql(scripts = "classpath:scripts/init_posts.sql")
    @Test
    void findByTagsTest() {
        var actual = postRepository.findByIdFullMode(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        actual.setTextParts(null);

        var expected = findPostHandler.findPreviewModeByTags("Кулинария", 10,1);
        Assertions.assertEquals(expected.posts().get(0), actual);

        var pageOfPosts = PageOfPostsResponse.builder().search("Кулинария").postSize(10).pageNumber(1).count(1).build();
        Assertions.assertEquals(expected.pageOfPosts(), pageOfPosts);
    }

    @Sql(scripts = "classpath:scripts/init_posts.sql")
    @Test
    void findAllTest() {
        var expected = findPostHandler.findPreviewModeByTags("", 100,1);
        Assertions.assertEquals(5, expected.posts().size());
    }
}
