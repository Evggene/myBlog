package org.bea.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Builder
@Getter
public class PostRequest {
    private UUID id;
    private String title;
    private String imagePath;
    private String textPreview;
    private List<String> textParts;
    private int likesCount;
    private List<String> tags;
    private List<Comment> comments;

    public String getTagsAsText() {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        return String.join( " ", tags);
    }

    public String getText() {
        return String.join( "\n", textParts);
    }

    @Data
    @Builder
    public static class Comment {
        private UUID id;
        private String content;
    }
}
