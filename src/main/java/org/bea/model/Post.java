package org.bea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bea.db.entity.CommentEntity;
import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private UUID id;
    private String title;
    private String imagePath;
    private String textPreview;
    private List<ParagraphEntity> textParts;
    private int likesCount;
    private Set<TagEntity> tags = new HashSet<>();
    private List<CommentEntity> comments;

//    public String getTagsAsText() {
//        if (tags == null || tags.length == 0) {
//            return null;
//        }
//        return String.join(" ", tags);
//    }
//
//    public String getText() {
//        return String.join("\n", textParts);
//    }
}
