package org.bea.dao;

import org.bea.config.CommonDaoTest;
import org.bea.db.entity.LikeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LikeDaoTest extends CommonDaoTest {

    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM likes");
        jdbcTemplate.execute("DELETE FROM posts");
    }

    @Test
    void createForPostTest() {
        likeDao.createForPost(createLike());
        var postWithLIke = likeDao.findByPostId(postId);
        Assertions.assertEquals(7, postWithLIke.getLikesCount());
    }

    @Test
    void incDecTest() {
        likeDao.createForPost(createLike());

        likeDao.increment(postId);
        var postWithLikeInc = likeDao.findByPostId(postId);
        Assertions.assertEquals(8, postWithLikeInc.getLikesCount());

        likeDao.decrement(postId);
        var postWithLikeDec = likeDao.findByPostId(postId);
        Assertions.assertEquals(7, postWithLikeDec.getLikesCount());
    }

    private LikeEntity createLike() {
        return LikeEntity.builder().likesCount(7).postId(postId).build();
    }
}
