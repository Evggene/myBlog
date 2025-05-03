package org.bea.service;

import org.bea.config.ServiceConfiguration;
import org.bea.dao.CommonDaoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = {ServiceConfiguration.class})
@Sql(scripts = "classpath:scripts/init_posts.sql")
public class CommonServiceContext extends CommonDaoContext {

    @Autowired
    protected AddPostHandler addPostHandler;
    @Autowired
    protected DeletePostHandler deletePostHandler;
    @Autowired
    protected EditPostHandler editPostHandler;
    @Autowired
    protected FindPostHandler findPostHandler;

}
