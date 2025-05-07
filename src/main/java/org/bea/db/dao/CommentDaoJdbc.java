package org.bea.db.dao;

import org.bea.db.entity.CommentEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CommentDaoJdbc extends BaseDao<CommentEntity> implements CommentDao {

    private final static String TABLE_NAME = "comments";
    public CommentDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public void update(CommentEntity commentEntity) {
        var sql = "UPDATE comments SET post_id = :postId, content = :content WHERE id = :id ";
        var params = new MapSqlParameterSource()
                .addValue("id", commentEntity.getId())
                .addValue("postId", commentEntity.getPostId())
                .addValue("content", commentEntity.getContent());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(UUID id, String byColumn) {
        super.delete(id, byColumn, TABLE_NAME);
    }

    @Override
    public CommentEntity setIdAndInsert(CommentEntity commentEntity) {
        return super.setIdAndInsert(commentEntity, TABLE_NAME);
    }

    @Override
    public CommentEntity findById(UUID id) {
        return super.findById(id, CommentEntity.class, TABLE_NAME);
    }

}
