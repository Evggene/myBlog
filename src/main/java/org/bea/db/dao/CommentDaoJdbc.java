package org.bea.db.dao;

import org.bea.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CommentDaoJdbc extends BaseDao<Comment> implements CommentDao {

    private final static String TABLE_NAME = "comments";
    public CommentDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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
