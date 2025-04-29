package org.bea.convertor;

import org.bea.db.entity.PostEntity;
import org.bea.model.Post;

public class PostConverter {
    PostEntity toEntity(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .textPreview(post.getTextPreview())
                .content(post.getContent())
                .imagePath(post.getImagePath())
                .build();
    }
}
