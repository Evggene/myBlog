package org.bea.presenter;

import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;
import org.bea.dto.PostRequest;
import org.bea.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostPresenter implements Presenter<Post, PostRequest> {
    public PostRequest toView(Post post) {

        var textParts = post.getTextParts().stream()
                .map(ParagraphEntity::getText)
                .toList();
        var tags = post.getTags().stream().map(TagEntity::getName).toList();
        var comments = post.getComments().stream()
                .map(it -> PostRequest.Comment.builder().id(it.getId()).content(it.getContent()).build())
                .toList();

        return PostRequest.builder()
                .id(post.getId())
                .title(post.getTitle())
                .textPreview(post.getTextPreview())
                .imagePath(post.getImagePath())
                .likesCount(post.getLikesCount())
                .textParts(textParts)
                .tags(tags)
                .comments(comments)
                .build();
    }
}
