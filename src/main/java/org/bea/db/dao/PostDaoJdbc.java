package org.bea.db.dao;

import org.bea.db.entity.Post;
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
public class PostDaoJdbc extends BaseDao<Post> implements PostDao {

    public PostDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    private final static String TABLE_NAME = "posts";
    private final static String COUNT_SQL_SELECT = """
            SELECT COUNT(*) FROM posts where deleted_at is null;
            """;
    private final static String SELECT_BY_ID = """
            SELECT p.*, l.likes_count , array_agg(t.name) as tags FROM posts p
            join likes l on l.post_id = p.id
            left join tags_to_post pt on pt.post_id = p.id
            left join tags t on t.id = pt.tag_id
            WHERE p.id = :COLUMN_ID
            group by p.id
            """;


    public List<Post> findAll() {
        var rowMapper = new BeanPropertyRowMapper<>(Post.class);
        Map<String, Object> paramMap = new HashMap<>();
        return namedParameterJdbcTemplate.query("select * from posts", paramMap, rowMapper);
    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject(COUNT_SQL_SELECT, Long.class);
    }

    @Override
    public Post setIdAndInsert(Post post) {
        return super.setIdAndInsert(post, TABLE_NAME);
    }

    @Override
    public Post findById(UUID id) {
        var rowMapper = new BeanPropertyRowMapper<>(Post.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query(
                "select * from posts p WHERE p.id = :COLUMN_ID and deleted_at is null", paramMap, rowMapper);
        if (!res.isEmpty()) {
            return res.getFirst();
        }
        return null;
    }

    @Override
    public void update(Post post) {
        var params = new MapSqlParameterSource();
        var sql = new StringBuilder("UPDATE posts SET ");
        if (!post.getImagePath().isBlank()) {
            sql.append("image_path = :imagePath, ");
            params.addValue("imagePath", post.getImagePath());
        }
        if (!post.getTitle().isBlank()) {
            sql.append("title = :title, ");
            params.addValue("title", post.getTitle());
        }
        if (!post.getTitle().isBlank()) {
            sql.append("text_preview = :textPreview ");
            params.addValue("textPreview", post.getTextPreview());
        }
        sql.append("WHERE id = :id; ");
        params.addValue("id", post.getId());
        namedParameterJdbcTemplate.update(sql.toString(), params);
    }

    @Override
    public void delete(UUID id) {
        super.delete(id, "id", TABLE_NAME);
    }

}
