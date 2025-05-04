package org.bea.service;

import org.assertj.core.api.Assertions;
import org.bea.model.PostAggregate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Comparator;

public class AddPostHandlerTest extends CommonServiceContext {

    // поля title и text не могут быть пустыми (валидация в контроллере)

    @Test
    void addPost_onlyTitleAndText_success() {
        addPostHandler.addPost("test", "test", "", "");
        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = PostAggregate.builder()
                .title("test")
                .textPreview("test")
                .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
                .tags(new String[]{null})
                .comments(new ArrayList<>())
                .build();

        Assertions.assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addPost_withTag_success() {
        addPostHandler.addPost("test", "test", "123", "");
        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = PostAggregate.builder()
                .title("test")
                .textPreview("test")
                .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
                .tags(new String[]{"123"})
                .comments(new ArrayList<>())
                .build();

        Assertions.assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addPost_withTags_success() {
        addPostHandler.addPost("test", "test", "123 456", "");
        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = PostAggregate.builder()
                .title("test")
                .textPreview("test")
                .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
                .tags(new String[]{"123", "456"})
                .comments(new ArrayList<>())
                .build();

        Assertions.assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addPost_withTwoParagraph_success() {
        addPostHandler.addPost("test", "test \n test2", "", "");
        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = PostAggregate.builder()
                .title("test")
                .textPreview("test")
                .textParts(new String[]{"test", "test2"})
                .likesCount(0)
                .imagePath("")
                .tags(new String[]{null})
                .comments(new ArrayList<>())
                .build();

        Assertions.assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addSecondPost_withSameAndAnotherTags_success() {
        addPostHandler.addPost("test", "test", "123 456", "");
        addPostHandler.addPost("test 2", "test", "456 789", "");

        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(2, postsPreviewMode.size());
        postsPreviewMode.sort(Comparator.comparing(PostAggregate::getTitle));
        var postFullMode0 = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(0).getId());
        var postFullMode1 = postAggregateRepository.findByIdFullMode(postsPreviewMode.get(1).getId());

        var postExpectedFullMode = PostAggregate.builder()
                .title("test")
                .textPreview("test")
                .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
                .tags(new String[]{"123", "456"})
                .comments(new ArrayList<>())
                .build();

        Assertions.assertThat(postFullMode0)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);

        postExpectedFullMode.setTitle("test 2");
        postExpectedFullMode.setTags(new String[]{"456", "789"});

        Assertions.assertThat(postFullMode1)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }
}
