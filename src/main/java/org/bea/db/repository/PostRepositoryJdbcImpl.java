package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
import org.bea.db.entity.PostEntity;
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
public class PostRepositoryJdbcImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String DEFAULT_LIMIT = "10";
    private final static String TABLE_NAME = "posts";
    private final static String SQL_SELECT = """
            SELECT p.*, l.likes_count , array_agg(t.name) as tags , array_agg(c.content) as comments  FROM posts p
            join likes l on l.post_id = p.id
left join posts_tags pt on pt.post_id = p.id
join tags t on t.id = pt.tag_id
left join comments c on c.post_id = p.id
group by p.id
            LIMIT :limit OFFSET :offset;
            """;
    private final static String COUNT_SQL_SELECT = """
            SELECT COUNT(*) FROM posts;
            """;
    private final static String SELECT_BY_ID = """
            SELECT p.*, l.likes_count , array_agg(t.name) as tags , array_agg(c.content) as comments  FROM posts p
            join likes l on l.post_id = p.id
            left join posts_tags pt on pt.post_id = p.id
            join tags t on t.id = pt.tag_id
            left join comments c on c.post_id = p.id
            WHERE p.id = :COLUMN_ID
            group by p.id
            """;
    @Override
    public List<PostEntity> findAll(int offset) {
        var rowMapper = new BeanPropertyRowMapper<>(PostEntity.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("offset", offset);
        paramMap.put("limit", DEFAULT_LIMIT);
        return namedParameterJdbcTemplate.query(SQL_SELECT, paramMap, rowMapper);
    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject(COUNT_SQL_SELECT, Long.class);
    }

    @Override
    public Post save(Post post) {
        post.setId(UUID.randomUUID());
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME);
        var paramSource = new BeanPropertySqlParameterSource(post);
        insert.execute(paramSource);
        return post;
    }

    @Override
    public PostEntity getById(UUID id) {
        var rowMapper = new BeanPropertyRowMapper<>(PostEntity.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query(SELECT_BY_ID, paramMap, rowMapper);
        if (!res.isEmpty()) {
            return res.getFirst();
        }
        return null;
    }

}
