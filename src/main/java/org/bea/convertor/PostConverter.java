package org.bea.convertor;

import org.bea.db.entity.PostAggregate;
import org.bea.model.Post;

public class PostConverter {
    PostAggregate toEntity(Post post) {
        return PostAggregate.builder()
                .id(post.getId())
                .title(post.getTitle())
                .textPreview(post.getTextPreview())
                .text(post.getText())
                .imagePath(post.getImagePath())
                .build();
    }
}
