package org.bea.db.dao;

import lombok.RequiredArgsConstructor;
import org.bea.db.entity.PostAggregate;
import org.bea.model.Comment;
import org.bea.model.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class CommentRepositoryJdbcImpl extends BaseRepository<Comment> implements CommentRepository {

    private final static String TABLE_NAME = "comments";
    public CommentRepositoryJdbcImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public void update(Comment comment) {
        var sql = "UPDATE comments SET post_id = :postId, content = :content WHERE id = :id ";
        var params = new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("postId", comment.getPostId())
                .addValue("content", comment.getContent());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(UUID id, String byColumn) {
        super.delete(id, byColumn, TABLE_NAME);
    }

    @Override
    public Comment setIdAndInsert(Comment comment) {
        return super.setIdAndInsert(comment, TABLE_NAME);
    }

}
