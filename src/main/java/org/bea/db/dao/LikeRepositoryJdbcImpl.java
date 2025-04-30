package org.bea.db.dao;

import lombok.RequiredArgsConstructor;
import org.bea.model.Like;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryJdbcImpl implements LikeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createForPost(Like like) {
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("likes");
        var paramSource = new BeanPropertySqlParameterSource(like);
        insert.execute(paramSource);
    }
}
