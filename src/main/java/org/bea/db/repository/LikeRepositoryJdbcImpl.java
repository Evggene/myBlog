package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
import org.bea.model.Like;
import org.bea.model.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
