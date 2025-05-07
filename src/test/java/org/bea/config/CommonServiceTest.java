package org.bea.config;

import org.bea.service.AddPostHandler;
import org.bea.service.DeletePostHandler;
import org.bea.service.EditPostHandler;
import org.bea.service.FindPostHandler;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonServiceTest extends CommonRepositoryTest {

    @Autowired
    protected AddPostHandler addPostHandler;
    @Autowired
    protected DeletePostHandler deletePostHandler;
    @Autowired
    protected EditPostHandler editPostHandler;
    @Autowired
    protected FindPostHandler findPostHandler;

    @AfterEach
    void truncateAllTable() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM likes");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM tags_to_post");
        jdbcTemplate.execute("DELETE FROM paragraphs");
        jdbcTemplate.execute("DELETE FROM comments");
    }
}
