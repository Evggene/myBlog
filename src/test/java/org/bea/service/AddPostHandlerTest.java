package org.bea.service;

import org.bea.config.CommonServiceTest;
import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;
import org.bea.model.Post;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPostHandlerTest extends CommonServiceTest {

    // поля title и text не могут быть пустыми (валидация в контроллере)

    @Test
    void addPost_onlyTitleAndText_success() {
        addPostHandler.addPost("test", "test", "", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = createPost();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id", "textParts")
                .isEqualTo(postExpectedFullMode);
        org.junit.jupiter.api.Assertions.assertEquals(1, postFullMode.getTextParts().size());
        org.junit.jupiter.api.Assertions.assertEquals("test", postFullMode.getTextParts().get(0).getText());
    }

    @Test
    void addPost_withTag_success() {
        addPostHandler.addPost("test", "test", "123", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = createPost();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id", "tags", "textParts")
                .isEqualTo(postExpectedFullMode);
        org.junit.jupiter.api.Assertions.assertEquals("test", postFullMode.getTextParts().get(0).getText());
        org.junit.jupiter.api.Assertions.assertEquals("123", postFullMode.getTags().get(0).getName());
    }

    @Test
    void addPost_withTags_success() {
        addPostHandler.addPost("test", "test", "123 456", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = createPost();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id", "tags", "textParts")
                .isEqualTo(postExpectedFullMode);
        org.junit.jupiter.api.Assertions.assertEquals("test", postFullMode.getTextParts().get(0).getText());
        org.junit.jupiter.api.Assertions.assertEquals("123", postFullMode.getTags().get(0).getName());
        org.junit.jupiter.api.Assertions.assertEquals("456", postFullMode.getTags().get(1).getName());
    }

    @Test
    void addPost_withTwoParagraph_success() {
        addPostHandler.addPost("test", "test \n test2", "", "");
        var postsPreviewMode = postRepository.findAllPreviewMode(0, 10);
        org.junit.jupiter.api.Assertions.assertEquals(1, postsPreviewMode.size());
        var postFullMode = postRepository.findByIdFullMode(postsPreviewMode.get(0).getId());

        var postExpectedFullMode = createPost();

        assertThat(postFullMode)
                .usingRecursiveComparison()
                .ignoringFields("id", "textParts")
                .isEqualTo(postExpectedFullMode);
        org.junit.jupiter.api.Assertions.assertEquals("test", postFullMode.getTextParts().get(0).getText());
        org.junit.jupiter.api.Assertions.assertEquals("test2", postFullMode.getTextParts().get(1).getText());
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

        var postExpectedFullMode = createPost();

        assertThat(postFullMode0)
                .usingRecursiveComparison()
                .ignoringFields("id", "tags", "textParts.id", "textParts.postId")
                .isEqualTo(postExpectedFullMode);
        org.junit.jupiter.api.Assertions.assertEquals("123", postFullMode0.getTags().get(0).getName());
        org.junit.jupiter.api.Assertions.assertEquals("456", postFullMode0.getTags().get(1).getName());

        postExpectedFullMode.setTitle("test 2");
        var tag1 = TagEntity.builder().id(UUID.randomUUID()).name("456").build();
        var tag2 = TagEntity.builder().id(UUID.randomUUID()).name("789").build();
       postExpectedFullMode.setTags(List.of(tag1, tag2));

        assertThat(postFullMode1)
                .usingRecursiveComparison()
                .ignoringFields("id", "tags.id", "textParts.id", "textParts.postId")
                .isEqualTo(postExpectedFullMode);
    }

    private static Post createPost() {
        return Post.builder()
                .title("test")
                .textPreview("test")
                .textParts(List.of(ParagraphEntity.builder().id(UUID.randomUUID()).text("test").ord(1).build()))
                .likesCount(0)
                .imagePath("")
                .tags(List.of())
                .comments(new ArrayList<>())
                .build();
    }
}
