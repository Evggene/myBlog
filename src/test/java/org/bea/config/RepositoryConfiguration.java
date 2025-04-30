package org.bea.config;

import org.bea.db.repository.PostRepository;
import org.bea.db.repository.PostRepositoryJdbcImpl;
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
    PostRepository postRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostRepositoryJdbcImpl(jdbcTemplate, namedParameterJdbcTemplate);
    }

}
