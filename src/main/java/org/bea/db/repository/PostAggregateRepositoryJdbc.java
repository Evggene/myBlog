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
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostAggregateRepositoryJdbc implements PostAggregateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BeanPropertyRowMapper<PostAggregate> rowMapper = new BeanPropertyRowMapper<>(PostAggregate.class);
    private final BeanPropertyRowMapper<Comment> commentMapper = new BeanPropertyRowMapper<>(Comment.class);

    private final static String SELECT_ALL_WITH_LIMIT_WITH_OFFSET = """
            SELECT
                p.*, coalesce(l.likes_count, 0) as likesCount,
                array_agg(t.name) as tags
            FROM posts p
                left join likes l on l.post_id = p.id
                left join tags_to_post pt on pt.post_id = p.id
                left join tags t on t.id = pt.tag_id
            WHERE p.deleted_at IS NULL
            group by p.id
            LIMIT :limit OFFSET :offset;
            """;

    private final static String SELECT_BY_ID = """
            select
                p.*,
                coalesce(l.likes_count, 0) as likesCount , 
                array_agg(t.name) as tags 
            from posts p
                left join likes l on l.post_id = p.id
                left join tags_to_post pt on pt.post_id = p.id
                left join tags t on t.id = pt.tag_id
            where p.id = :COLUMN_ID
            group by p.id
            """;

    private final static String SELECT_ALL_COMMENTS = """
            SELECT * from comments where post_id = :postId AND deleted_at IS NULL;
            """;

    @Override
    public List<PostAggregate> findAll(int offset, int limit) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("offset", offset);
        paramMap.put("limit", limit);
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

    @Override
    public void delete(UUID id) {

    }
}
