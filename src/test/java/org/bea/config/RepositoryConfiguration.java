package org.bea.config;

import org.bea.db.dao.PostDao;
import org.bea.db.dao.PostDaoJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@PropertySource("classpath:application.properties")

public class RepositoryConfiguration {

    @Primary
    @Bean
    PostDao postRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostDaoJdbc(jdbcTemplate, namedParameterJdbcTemplate);
    }

}
