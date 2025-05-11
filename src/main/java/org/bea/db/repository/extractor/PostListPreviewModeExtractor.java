package org.bea.db.repository.extractor;

import org.bea.db.entity.TagEntity;
import org.bea.model.Post;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostListPreviewModeExtractor implements ResultSetExtractor<Void> {

    public static final String postsSqlWithTag = """
        SELECT
            p.id,
            p.title,
            p.text_preview,
            p.image_path,
            COALESCE(l.likes_count, 0) as likes_count 
        FROM posts p 
        LEFT JOIN likes l ON l.post_id = p.id
        WHERE p.id IN (
            SELECT DISTINCT pt.post_id 
            FROM tags_to_post pt
            LEFT JOIN tags t ON t.id = pt.tag_id WHERE pt.deleted_at IS NULL
            and t.id in (:tagList)
        )
        AND p.deleted_at IS NULL
        ORDER BY p.updated_at DESC
        LIMIT :limit OFFSET :offset
        """;

    public static final String postsSqlWithoutTag = """
        SELECT
            p.id,
            p.title,
            p.text_preview,
            p.image_path,
            COALESCE(l.likes_count, 0) as likes_count
        FROM posts p
        LEFT JOIN likes l ON l.post_id = p.id AND p.deleted_at IS NULL
        WHERE p.deleted_at is null
        ORDER BY p.updated_at DESC
        LIMIT :limit OFFSET :offset
        """;

    public static final String tagsSql = """
        SELECT
            pt.post_id,
            t.id,
            t.name
        FROM tags_to_post pt
        LEFT JOIN tags t ON t.id = pt.tag_id WHERE pt.deleted_at IS NULL
        AND pt.post_id IN (:postIds)
        """;

    private final Map<UUID, Post> postsMap;

    public PostListPreviewModeExtractor(List<Post> posts) {
        this.postsMap = posts.stream()
                .peek(it -> it.setTags(new ArrayList<>()))
                .collect(Collectors.toMap(Post::getId, Function.identity()));
    }

    @Override
    public Void extractData(ResultSet rs) throws SQLException {
        while (rs.next()) {
            var postId = rs.getObject("post_id", UUID.class);
            var post = postsMap.get(postId);

            if (post != null) {
                post.getTags().add(TagEntity.builder()
                        .id(rs.getObject("id", UUID.class))
                        .name(rs.getString("name"))
                        .build());
            }
        }
        return null;
    }
}

