package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
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
    private final static String DEFAULT_LIMIT = "10";
    private final static String TABLE_NAME = "posts";
    private final static String SQL_SELECT = """
            SELECT * FROM posts LIMIT :limit OFFSET :offset;
            """;
    private final static String COUNT_SQL_SELECT = """
            SELECT COUNT(*) FROM posts;
            """;
    private final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = :COLUMN_ID";
    @Override
    public List<Post> findAll(int offset) {
        var rowMapper = new BeanPropertyRowMapper<>(Post.class);
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
    public void save(Post post) {
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME);
        var paramSource = new BeanPropertySqlParameterSource(post);
        insert.execute(paramSource);
    }

    @Override
    public Post getById(UUID id) {
        var rowMapper = new BeanPropertyRowMapper<>(Post.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query(SELECT_BY_ID, paramMap, rowMapper);
        if (!res.isEmpty()) {
            return res.getFirst();
        }
        return null;
    }

}
