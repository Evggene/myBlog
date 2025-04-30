package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.LikeRepository;
import org.bea.db.dao.PostRepository;
import org.bea.db.dao.TagRepository;
import org.bea.model.Like;
import org.bea.model.Post;
import org.bea.model.Tag;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddPostHandler {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final TagRepository tagRepository;

    public void addPost(String title, String text, String tags, String originalFilename) {
        handleTags(tags);
        var post = handlePost(title, text, originalFilename);
        handleLike(post);
    }

    private void handleTags(String tags) {
        if (tags.isBlank()) {
            return;
        }
        var tagsUnique = convertToSet(tags);
        var tagsList = buildTags(tagsUnique);
        tagsList.forEach(tagRepository::setIdAndInsert);
    }

    private Post handlePost(String title, String text, String originalFilename) {
        var post = buildPost(title, text, originalFilename);
        postRepository.setIdAndInsert(post);
        return post;
    }

    private void handleLike(Post post) {
        var like = buildLike(post);
        likeRepository.createForPost(like);
    }


    private Set<String> convertToSet(String tags) {
        return Arrays.stream(tags.split(" "))
                .collect(Collectors.toSet());
    }

    private Set<Tag> buildTags(Set<String> tagsUnique) {
        return tagsUnique.stream()
                .map(it -> Tag.builder().name(it).build())
                .collect(Collectors.toSet());
    }

    private Post buildPost(String title, String text, String originalFilename) {
        return Post.builder()
                .title(title)
                .imagePath(originalFilename)
                .textPreview(text)
                .content(text)
                .build();
    }

    private Like buildLike(Post post) {
        return Like.builder()
                .postId(post.getId())
                .likesCount(0)
                .build();
    }

}

