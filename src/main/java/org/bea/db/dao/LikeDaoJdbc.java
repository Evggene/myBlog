package org.bea.db.dao;

import lombok.RequiredArgsConstructor;
import org.bea.db.entity.LikeEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LikeDaoJdbc implements LikeDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE_NAME = "likes";

    @Override
    public void createForPost(LikeEntity likeEntity) {
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("likes");
        var paramSource = new BeanPropertySqlParameterSource(likeEntity);
        insert.execute(paramSource);
    }

    @Override
    public void increment(UUID postId) {
        var sql = "UPDATE likes SET likes_count = likes_count + 1 WHERE post_id = :id";
        var params = new MapSqlParameterSource()
                .addValue("id", postId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void decrement(UUID postId) {
        var sql = "UPDATE likes SET likes_count = likes_count - 1 WHERE post_id = :id";
        var params = new MapSqlParameterSource()
                .addValue("id", postId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public LikeEntity findByPostId(UUID id) {
        var rowMapper = new BeanPropertyRowMapper<>(LikeEntity.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        return namedParameterJdbcTemplate.query(
                "select * from " + TABLE_NAME + " WHERE post_id = :COLUMN_ID", paramMap, rowMapper).getFirst();
    }
}
