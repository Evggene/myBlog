package org.bea.db.repository.extractor;

import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;
import org.bea.model.Post;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostByIdFullModeExtractor implements ResultSetExtractor<Post> {

    public static final String sql = """
        SELECT
            p.id,
            p.title,
            p.text_preview,
            p.image_path,
            COALESCE(l.likes_count, 0) as likes_count,
            t.id as tag_id,
            t.name as tag_name,
            pg.id as paragraph_id,
            pg.ord as paragraph_ord,
            pg.text as paragraph_text
        FROM posts p
        LEFT JOIN likes l ON l.post_id = p.id
        LEFT JOIN tags_to_post pt ON pt.post_id = p.id AND pt.deleted_at IS NULL
        LEFT JOIN tags t ON t.id = pt.tag_id
        LEFT JOIN paragraphs pg ON pg.post_id = p.id AND pg.deleted_at IS NULL
        WHERE p.id = :postId
    """;
    @Override
    public Post extractData(ResultSet rs) throws SQLException {
        Post post = null;
        Set<TagEntity> tags = new HashSet<>();
        Set<ParagraphEntity> paragraphs = new HashSet<>();

        while (rs.next()) {
            if (post == null) {
                post = Post.builder()
                        .id((UUID) rs.getObject("id"))
                        .title(rs.getString("title"))
                        .imagePath(rs.getString("image_path"))
                        .textPreview(rs.getString("text_preview"))
                        .likesCount(rs.getInt("likes_count"))
                        .textParts(new ArrayList<>())
                        .tags(new HashSet<>())
                        .comments(new ArrayList<>())
                        .build();
            }
            UUID tagId = (UUID) rs.getObject("tag_id");
            if (tagId != null) {
                tags.add(TagEntity.builder()
                        .id(tagId)
                        .name(rs.getString("tag_name"))
                        .build());
            }
            UUID paragraphId = (UUID) rs.getObject("paragraph_id");
            if (paragraphId != null) {
                paragraphs.add(ParagraphEntity.builder()
                        .id(paragraphId)
                        .postId(post.getId())
                        .ord(rs.getInt("paragraph_ord"))
                        .text(rs.getString("paragraph_text"))
                        .build());
            }
            post.setLikesCount(rs.getInt("likes_count"));
        }
        if (post != null) {
            post.setTags(tags);
            post.setTextParts(paragraphs.stream()
                    .sorted(Comparator.comparingInt(ParagraphEntity::getOrd))
                    .collect(Collectors.toList()));
        }
        return post;
    }
}
