package org.bea.service;

import org.bea.config.ServiceConfiguration;
import org.bea.dao.CommonDaoContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ContextConfiguration(classes = {ServiceConfiguration.class})
public class CommonServiceContext extends CommonDaoContext {

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
