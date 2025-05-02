package org.bea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bea.db.entity.Comment;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAggregate {
    private UUID id;
    private String title;
    private String imagePath;
    private String textPreview;
    private String[] textParts;
    private int likesCount;
    private String[] tags;
    private List<Comment> comments;

    public String getTagsAsText() {
        if (tags == null || tags.length == 0) {
            return null;
        }
        return String.join(" ", tags);
    }

    public String getText() {
        return String.join("\n", textParts);
    }
}
