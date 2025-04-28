package org.bea.config;

import org.bea.repository.PostRepository;
import org.bea.repository.PostRepositoryJdbcImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataTestConfiguration {

    @Bean
    PostRepository postRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostRepositoryJdbcImpl(jdbcTemplate, namedParameterJdbcTemplate);
    }


}
