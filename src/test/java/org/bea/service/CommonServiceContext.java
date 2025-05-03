package org.bea.service;

import org.bea.config.ServiceConfiguration;
import org.bea.dao.CommonDaoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ServiceConfiguration.class})
public class CommonServiceContext extends CommonDaoContext {

    @Autowired
    protected AddPostHandler addPostHandler;
    @Autowired
    protected DeletePostHandler deletePostHandler;
    @Autowired
    protected EditPostHandler editPostHandler;

}
