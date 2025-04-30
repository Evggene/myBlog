package org.bea.db.repository;

import org.bea.db.entity.PostAggregate;
import org.bea.model.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PostRepositoryJdbcImpl extends BaseRepository<Post> implements PostRepository {

    public PostRepositoryJdbcImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    private final static String DEFAULT_LIMIT = "10";
    private final static String TABLE_NAME = "posts";
    private final static String SQL_SELECT = """
            SELECT p.*, l.likes_count , array_agg(t.name) as tags , array_agg(c.content) as comments  FROM posts p
            left join likes l on l.post_id = p.id
            left join posts_tags pt on pt.post_id = p.id
            left join tags t on t.id = pt.tag_id
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
    public List<PostAggregate> findAll(int offset) {
        var rowMapper = new BeanPropertyRowMapper<>(PostAggregate.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("offset", offset);
        paramMap.put("limit", DEFAULT_LIMIT);
        return namedParameterJdbcTemplate.query(SQL_SELECT, paramMap, rowMapper);
    }


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
    public PostAggregate getById(UUID id) {
        var rowMapper = new BeanPropertyRowMapper<>(PostAggregate.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query(SELECT_BY_ID, paramMap, rowMapper);
        if (!res.isEmpty()) {
            return res.getFirst();
        }
        return null;
    }

    @Override
    public Post findPostById(UUID id) {
        var rowMapper = new BeanPropertyRowMapper<>(Post.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query("select * from posts p WHERE p.id = :COLUMN_ID", paramMap, rowMapper);
        if (!res.isEmpty()) {
            return res.getFirst();
        }
        return null;
    }

}
