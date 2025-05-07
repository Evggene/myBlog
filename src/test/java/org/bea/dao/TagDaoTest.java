package org.bea.dao;

import org.bea.config.CommonDaoTest;
import org.bea.db.entity.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TagDaoTest extends CommonDaoTest {

    private final UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");
    private final UUID tagId1 = UUID.fromString("50000000-0000-0000-0000-000000000001");
    private final String tagName1 = "technology";
    private final String tagName2 = "science";

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM tags_to_post");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM posts");
    }

    @Test
    void findByNameTest() {
        var newTag = createTagWithoutId();
        tagDao.setIdAndInsert(newTag);

        var savedTag = tagDao.findByName(tagName1);

        assertEquals(newTag.getName(), savedTag.getName());
    }

    @Test
    void findByIdTest() {
        var newTag = createTagWithoutId();
        var tagSaved = tagDao.setIdAndInsert(newTag);

        var tag = tagDao.findById(tagSaved.getId());
        assertEquals(tagName1, tag.getName());
    }

    private TagEntity createTagWithoutId() {
        return TagEntity.builder().name(tagName1).build();
    }
}
