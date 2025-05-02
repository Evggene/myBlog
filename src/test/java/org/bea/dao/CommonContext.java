package org.bea.dao;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.RepositoryConfiguration;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.repository.PostAggregateRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfigurationTest.class, RepositoryConfiguration.class})
public class CommonContext {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected ParagraphDao paragraphDao;
    @Autowired
    protected PostDao postDao;
    @Autowired
    protected PostAggregateRepository postAggregateRepository;
}
