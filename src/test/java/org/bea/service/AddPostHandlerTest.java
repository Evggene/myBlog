package org.bea.service;

import org.assertj.core.api.Assertions;
import org.bea.model.PostAggregate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AddPostHandlerTest extends CommonServiceContext {

    // поля title и text не могут быть пустыми (валидация в контроллере)

    @Test
    void addPost_onlyTitleAndText_success() {
        addPostHandler.addPost("test", "test", "", "");
        var postsPreviewMode = postAggregateRepository.findAllPreviewMode(0, 1);
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
        addPostHandler.addPost("test", "test", "", "");
        var post = postAggregateRepository.findAllPreviewMode(0, 1);
        System.out.println();
    }

    @Test
    void addPost_withTags_success() {
        addPostHandler.addPost("test", "test", "", "");
        var post = postAggregateRepository.findAllPreviewMode(0, 1);
        System.out.println();
    }

    @Test
    void addPost_withTwoParagraph_success() {
        addPostHandler.addPost("test", "test", "", "");
        var post = postAggregateRepository.findAllPreviewMode(0, 1);
        System.out.println();
    }

    @Test
    void addSecondPost_withSameTags_success() {
        addPostHandler.addPost("test", "test", "", "");
        var post = postAggregateRepository.findAllPreviewMode(0, 1);
        System.out.println();
    }

    @Test
    void addSecondPost_withAnotherTags_success() {
        addPostHandler.addPost("test", "test", "", "");
        var post = postAggregateRepository.findAllPreviewMode(0, 1);
        System.out.println();
    }
}
