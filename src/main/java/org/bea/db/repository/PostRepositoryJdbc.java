package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
import org.bea.db.entity.CommentEntity;
import org.bea.db.entity.TagEntity;
import org.bea.db.repository.extractor.PostByIdFullModeExtractor;
import org.bea.db.repository.extractor.PostListPreviewModeExtractor;
import org.bea.model.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepositoryJdbc implements PostRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BeanPropertyRowMapper<Post> rowMapper = new BeanPropertyRowMapper<>(Post.class);
    private final BeanPropertyRowMapper<CommentEntity> commentMapper = new BeanPropertyRowMapper<>(CommentEntity.class);

    private final static String SELECT_ALL_COMMENTS = """
            SELECT * from comments where post_id = :postId AND deleted_at IS NULL order by updated_at;
            """;

    @Override
    public List<Post> findAllPreviewMode(int offset, int limit) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("offset", offset);
        paramMap.put("limit", limit);
        var posts = namedParameterJdbcTemplate.query(PostListPreviewModeExtractor.postsSqlWithoutTag, paramMap, rowMapper);
        List<UUID> postIds = posts.stream().map(Post::getId).toList();
        var extractor = new PostListPreviewModeExtractor(posts);
        namedParameterJdbcTemplate.query(
                PostListPreviewModeExtractor.tagsSql,
                Map.of("postIds", postIds),
                extractor
        );
        return posts;
    }

    private Post findCommentsAndSet(Post it) {
        var commentParamMap = new HashMap<String, Object>();
        commentParamMap.put("postId", it.getId().toString());
        var comments = namedParameterJdbcTemplate.query(SELECT_ALL_COMMENTS, commentParamMap, commentMapper);
        it.setComments(comments);
        return it;
    }

    @Override
    public Post findByIdFullMode(UUID id) {
        var res = namedParameterJdbcTemplate.query(
                PostByIdFullModeExtractor.sql,
                Collections.singletonMap("postId", id),
                new PostByIdFullModeExtractor());
        return findCommentsAndSet(res);
    }

    @Override
    public List<Post> findByTagPreviewMode(List<TagEntity> tags, int offset, int limit) {
        var tagIdsForSql = tags.stream()
                .map(it -> it.getId().toString())
                .toList();
        var paramMap = new HashMap<String, Object>();
        paramMap.put("offset", offset);
        paramMap.put("limit", limit);
        paramMap.put("tagList", tagIdsForSql);
        var posts = namedParameterJdbcTemplate.query(PostListPreviewModeExtractor.postsSqlWithTag, paramMap, rowMapper);
        List<UUID> postIds = posts.stream().map(Post::getId).toList();
        var extractor = new PostListPreviewModeExtractor(posts);
        namedParameterJdbcTemplate.query(
                PostListPreviewModeExtractor.tagsSql,
                Map.of("postIds", postIds),
                extractor
        );
        return posts;
    }
}
