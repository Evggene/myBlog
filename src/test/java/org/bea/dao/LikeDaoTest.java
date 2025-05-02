package org.bea.dao;

import org.bea.db.entity.Like;
import org.bea.service.LikeActionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LikeDaoTest extends CommonDaoContext {

    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM likes");
        jdbcTemplate.execute("DELETE FROM posts");

        jdbcTemplate.execute(
                """
                INSERT INTO posts(id, title, text_preview, image_path)
                    VALUES
                ('20000000-0000-0000-0000-000000000001',
                'Test Post',
                'Test Preview',
                'test.png');
                """);
    }

    @Test
    void createForPostTest() {
        likeDao.createForPost(createLike());
        var postWithLIke = postAggregateRepository.findById(postId);
        Assertions.assertEquals(7, postWithLIke.getLikesCount());
    }

    @Test
    void incDecTest() {
        likeDao.createForPost(createLike());

        likeDao.incDec(postId, LikeActionHandler.LikeActionType.INCREMENT);
        var postWithLikeInc = postAggregateRepository.findById(postId);
        Assertions.assertEquals(8, postWithLikeInc.getLikesCount());

        likeDao.incDec(postId, LikeActionHandler.LikeActionType.DECREMENT);
        var postWithLikeDec = postAggregateRepository.findById(postId);
        Assertions.assertEquals(7, postWithLikeDec.getLikesCount());
    }

    private Like createLike() {
        return Like.builder().likesCount(7).postId(postId).build();
    }
}
