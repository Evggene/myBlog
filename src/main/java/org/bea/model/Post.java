package org.bea.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Post {
    private UUID postId;
    private String title;
    private String imagePath;
    private String content;
    private String previewContent;
}
