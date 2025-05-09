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

    private final static String SELECT_ALL_COMMENTS = """
            SELECT * from comments where post_id = :postId AND deleted_at IS NULL order by updated_at;
            """;

    @Override
    public List<Post> findAllPreviewMode(int offset, int limit) {
        var paramMap = Map.of("offset", offset, "limit", limit);
        var posts = namedParameterJdbcTemplate.query(PostListPreviewModeExtractor.postsSqlWithoutTag, paramMap, rowMapper);
        var postIds = posts.stream().map(Post::getId).toList();
        namedParameterJdbcTemplate.query(
                PostListPreviewModeExtractor.tagsSql, Map.of("postIds", postIds), new PostListPreviewModeExtractor(posts));
        return posts;
    }

    private Post findCommentsAndSet(Post it) {
        var commentMapper = new BeanPropertyRowMapper<>(CommentEntity.class);
        var commentParamMap = Map.of("postId", it.getId().toString());
        var comments = namedParameterJdbcTemplate.query(SELECT_ALL_COMMENTS, commentParamMap, commentMapper);
        it.setComments(comments);
        return it;
    }

    @Override
    public Post findByIdFullMode(UUID id) {
        var res = namedParameterJdbcTemplate.query(
                PostByIdFullModeExtractor.sql, Collections.singletonMap("postId", id), new PostByIdFullModeExtractor());
        if (res == null) {
            return null;
        }
        return findCommentsAndSet(res);
    }

    @Override
    public List<Post> findByTagPreviewMode(List<TagEntity> tags, int offset, int limit) {
        var tagIdsForSql = tags.stream()
                .map(it -> it.getId().toString())
                .toList();
        var paramMap = Map.of("offset", offset, "limit", limit, "tagList", tagIdsForSql);
        var posts = namedParameterJdbcTemplate.query(PostListPreviewModeExtractor.postsSqlWithTag, paramMap, rowMapper);
        var postIds = posts.stream().map(Post::getId).toList();
        namedParameterJdbcTemplate.query(
                PostListPreviewModeExtractor.tagsSql, Map.of("postIds", postIds), new PostListPreviewModeExtractor(posts));
        return posts;
    }
}
