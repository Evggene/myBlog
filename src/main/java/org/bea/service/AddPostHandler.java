package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.repository.LikeRepository;
import org.bea.db.repository.PostRepository;
import org.bea.model.Like;
import org.bea.model.Post;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPostHandler {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    public void addPost(String title, String text, String tags, String originalFilename) {
        var post = Post.builder()
                .title(title)
                .imagePath(originalFilename)
                .textPreview(text)
                .content(text)
                .build();
        postRepository.save(post);
        var like = Like.builder()
                .postId(post.getId())
                .likesCount(0)
                .build();
        likeRepository.createForPost(like);
    }
}
