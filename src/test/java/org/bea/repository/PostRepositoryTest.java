package org.bea.repository;

import org.bea.config.CommonRepositoryTest;
import org.bea.config.CommonDaoTest;
import org.bea.db.entity.TagEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

@Sql(scripts = "classpath:scripts/init_posts.sql")
public class PostRepositoryTest extends CommonRepositoryTest {

    @AfterEach
    void truncateAllTable() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM likes");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM tags_to_post");
        jdbcTemplate.execute("DELETE FROM paragraphs");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    @Test
    void findByIdFullModeTest() {
        var post = postRepository.findByIdFullMode(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"));
        Assertions.assertEquals("Как улучшить качество сна: 5 научно доказанных методов", post.getTitle());
    }

    @Test
    void findAllPreviewModeOffsetTest() {
        var posts = postRepository.findAllPreviewMode(1, 10);
        Assertions.assertEquals(4, posts.size());
    }

    @Test
    void findAllPreviewModeLimitTest() {
        var posts = postRepository.findAllPreviewMode(0, 2);
        Assertions.assertEquals(2, posts.size());
    }

    @Test
    void findByTagPreviewModeTest() {
        var tag = TagEntity.builder().id(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14")).name("Здоровье").build();
        var posts = postRepository.findByTagPreviewMode(List.of(tag), 0, 10);
        Assertions.assertEquals(1, posts.size());
        Assertions.assertEquals("Как улучшить качество сна: 5 научно доказанных методов", posts.get(0).getTitle());
    }

    @Test
    void findByTagsPreviewModeTest() {
        var tag1 = TagEntity.builder().id(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14")).name("Здоровье").build();
        var tag2 = TagEntity.builder().id(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13")).name("ИТ").build();
        var posts = postRepository.findByTagPreviewMode(List.of(tag1, tag2), 0, 10);
        Assertions.assertEquals(2, posts.size());
        Assertions.assertEquals("React 18: что изменилось для разработчиков", posts.get(0).getTitle());
    }
}
