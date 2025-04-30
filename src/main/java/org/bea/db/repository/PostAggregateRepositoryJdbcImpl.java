package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
import org.bea.db.entity.PostAggregate;
import org.bea.model.Comment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostAggregateRepositoryJdbcImpl implements PostAggregateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BeanPropertyRowMapper<PostAggregate> rowMapper = new BeanPropertyRowMapper<>(PostAggregate.class);
    private final BeanPropertyRowMapper<Comment> commentMapper = new BeanPropertyRowMapper<>(Comment.class);

    private final static String SELECT_ALL_WITH_LIMIT_WITH_OFFSET = """
            SELECT p.*, coalesce(l.likes_count, 0) as likesCount, array_agg(t.name) as tags  FROM posts p
            left join likes l on l.post_id = p.id
            left join posts_tags pt on pt.post_id = p.id
            left join tags t on t.id = pt.tag_id
            group by p.id
            LIMIT :limit OFFSET :offset;
            """;

    private final static String SELECT_BY_ID = """
            SELECT p.*, coalesce(l.likes_count, 0) as likesCount , array_agg(t.name) as tags FROM posts p
            join likes l on l.post_id = p.id
            left join posts_tags pt on pt.post_id = p.id
            join tags t on t.id = pt.tag_id
            WHERE p.id = :COLUMN_ID
            group by p.id
            """;

    private final static String SELECT_ALL_COMMENTS = """
            SELECT * from comments where post_id = :postId AND deleted_at IS NULL;
            """;

    @Override
    public List<PostAggregate> findAll(int offset) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("offset", offset);
        paramMap.put("limit", 10);
        var posts = namedParameterJdbcTemplate.query(SELECT_ALL_WITH_LIMIT_WITH_OFFSET, paramMap, rowMapper);
        posts.forEach(this::findCommentsAndSet);
        return posts;
    }

    private PostAggregate findCommentsAndSet(PostAggregate it) {
        var commentParamMap = new HashMap<String, Object>();
        commentParamMap.put("postId", it.getId().toString());
        var comments = namedParameterJdbcTemplate.query(SELECT_ALL_COMMENTS, commentParamMap, commentMapper);
        it.setComments(comments);
        return it;
    }

    @Override
    public PostAggregate findById(UUID id) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query(SELECT_BY_ID, paramMap, rowMapper);
        if (!res.isEmpty()) {
            var unoRes = res.getFirst();
            return findCommentsAndSet(unoRes);
        }
        return null;
    }
}
