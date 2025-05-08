package org.bea.service;

import org.bea.config.CommonServiceTest;
import org.bea.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPostHandlerTest extends CommonServiceTest {

    // поля title и text не могут быть пустыми (валидация в контроллере)

    @Test
    void addPost_onlyTitleAndText_success() {
        addPostHandler.addPost("test", "test", "", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = Post.builder()
                .title("test")
                .textPreview("test")
            //    .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
            //    .tags(new String[]{null})
                .comments(new ArrayList<>())
                .build();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addPost_withTag_success() {
        addPostHandler.addPost("test", "test", "123", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = Post.builder()
                .title("test")
                .textPreview("test")
             //   .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
              //  .tags(new String[]{"123"})
                .comments(new ArrayList<>())
                .build();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addPost_withTags_success() {
        addPostHandler.addPost("test", "test", "123 456", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = Post.builder()
                .title("test")
                .textPreview("test")
            //    .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
              //  .tags(new String[]{"123", "456"})
                .comments(new ArrayList<>())
                .build();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addPost_withTwoParagraph_success() {
        addPostHandler.addPost("test", "test \n test2", "", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = Post.builder()
                .title("test")
                .textPreview("test")
            //    .textParts(new String[]{"test", "test2"})
                .likesCount(0)
                .imagePath("")
              //  .tags(new String[]{null})
                .comments(new ArrayList<>())
                .build();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }

    @Test
    void addSecondPost_withSameAndAnotherTags_success() {
        addPostHandler.addPost("test", "test", "123 456", "");
        addPostHandler.addPost("test 2", "test", "456 789", "");

        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(2, postsPreviewMode.size());
        postsPreviewMode.sort(Comparator.comparing(Post::getTitle));
        var postFullMode0 = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());
        var postFullMode1 = postRepository.findByIdFullMode(postsPreviewMode.get(1).getId());

        var postExpectedFullMode = Post.builder()
                .title("test")
                .textPreview("test")
             //   .textParts(new String[]{"test"})
                .likesCount(0)
                .imagePath("")
            //    .tags(new String[]{"123", "456"})
                .comments(new ArrayList<>())
                .build();

        assertThat(postFullMode0)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);

        postExpectedFullMode.setTitle("test 2");
       // postExpectedFullMode.setTags(new String[]{"456", "789"});

        assertThat(postFullMode1)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(postExpectedFullMode);
    }
}
