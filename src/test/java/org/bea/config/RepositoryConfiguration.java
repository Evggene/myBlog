package org.bea.config;

import org.bea.db.dao.LikeDao;
import org.bea.db.dao.LikeDaoJdbc;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.ParagraphDaoJdbc;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.PostDaoJdbc;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.db.repository.PostAggregateRepositoryJdbc;
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

    @Primary
    @Bean
    ParagraphDao paragraphDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new ParagraphDaoJdbc(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Primary
    @Bean
    PostAggregateRepository postAggregateRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostAggregateRepositoryJdbc(namedParameterJdbcTemplate);
    }

    @Primary
    @Bean
    LikeDao likeDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new LikeDaoJdbc(jdbcTemplate, namedParameterJdbcTemplate);
    }

}
