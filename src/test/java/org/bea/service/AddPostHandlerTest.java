package org.bea.service;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.RepositoryConfiguration;
import org.bea.config.ServiceConfiguration;
import org.bea.dao.CommonDaoContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

public class AddPostHandlerTest extends CommonServiceContext {

    // поля title и text не могут быть пустыми (валидация в контроллере)
    @Test
    void addPost_onlyTitleAndText_success() {
        addPostHandler.addPost("test", "test", "", "");
        var post = postAggregateRepository.findAll(0, 1);
        System.out.println();
    }
}
