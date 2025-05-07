package org.bea.db.dao;

import org.bea.db.entity.PostEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PostDaoJdbc extends BaseDao<PostEntity> implements PostDao {

    public PostDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    private final static String TABLE_NAME = "posts";
    private final static String COUNT_SQL_SELECT = """
            SELECT COUNT(*) FROM posts where deleted_at is null;
            """;
    private final static String SELECT_BY_ID = """
            SELECT p.*, l.likes_count , array_agg(t.name) as tagEntities FROM posts p
            join likes l on l.post_id = p.id
            left join tags_to_post pt on pt.post_id = p.id
            left join tagEntities t on t.id = pt.tag_id
            WHERE p.id = :COLUMN_ID
            group by p.id
            """;


    public List<PostEntity> findAll() {
        var rowMapper = new BeanPropertyRowMapper<>(PostEntity.class);
        Map<String, Object> paramMap = new HashMap<>();
        return namedParameterJdbcTemplate.query("select * from posts", paramMap, rowMapper);
    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject(COUNT_SQL_SELECT, Long.class);
    }

    @Override
    public PostEntity setIdAndInsert(PostEntity postEntity) {
        return super.setIdAndInsert(postEntity, TABLE_NAME);
    }

    @Override
    public PostEntity findById(UUID id) {
        return super.findById(id, PostEntity.class, TABLE_NAME);
    }

    @Override
    public void update(PostEntity postEntity) {
        var params = new MapSqlParameterSource();
        var sql = new StringBuilder("UPDATE posts SET ");
        if (!postEntity.getImagePath().isBlank()) {
            sql.append("image_path = :imagePath, ");
            params.addValue("imagePath", postEntity.getImagePath());
        }
        if (!postEntity.getTitle().isBlank()) {
            sql.append("title = :title, ");
            params.addValue("title", postEntity.getTitle());
        }
        if (!postEntity.getTitle().isBlank()) {
            sql.append("text_preview = :textPreview ,");
            params.addValue("textPreview", postEntity.getTextPreview());
        }
        super.setUpdatedAt(sql, params);
        sql.append("WHERE id = :id; ");
        params.addValue("id", postEntity.getId());
        namedParameterJdbcTemplate.update(sql.toString(), params);
    }


    @Override
    public void delete(UUID id) {
        super.delete(id, "id", TABLE_NAME);
    }

}
