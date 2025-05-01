package org.bea.db.dao;

import lombok.RequiredArgsConstructor;
import org.bea.model.Like;
import org.bea.service.LikeActionHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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

    @Override
    public void incDec(UUID postId, LikeActionHandler.LikeActionType actionType) {
        var sql = new StringBuilder("UPDATE likes SET likes_count = likes_count ");
        if (LikeActionHandler.LikeActionType.INCREMENT == actionType) {
            sql.append("+ 1 ");
        } else {
            sql.append("- 1 ");
        }
        sql.append("WHERE post_id = :id");
        var params = new MapSqlParameterSource()
                .addValue("id", postId);
        namedParameterJdbcTemplate.update(sql.toString(), params);
    }
}
