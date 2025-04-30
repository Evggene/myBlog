package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.repository.LikeRepository;
import org.bea.db.repository.PostRepository;
import org.bea.db.repository.TagRepository;
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
        var tagsUnique = convertToSet(tags);
        var tagsList = buildTags(tagsUnique);
        tagsList.forEach(tagRepository::save);
        var post = buildPost(title, text, originalFilename);
        postRepository.save(post);
        var like = buildLike(post);
        likeRepository.createForPost(like);
    }

    private static Set<Tag> buildTags(Set<String> tagsUnique) {
        var tagsList = tagsUnique.stream()
                .map(it -> Tag.builder().name(it).build())
                .collect(Collectors.toSet());
        return tagsList;
    }

    private Set<String> convertToSet(String tags) {
        return Arrays.stream(tags.split(" "))
                .collect(Collectors.toSet());
    }

    private Like buildLike(Post post) {
        return Like.builder()
                .postId(post.getId())
                .likesCount(0)
                .build();
    }

    private Post buildPost(String title, String text, String originalFilename) {
        return Post.builder()
                .title(title)
                .imagePath(originalFilename)
                .textPreview(text)
                .content(text)
                .build();
    }
}
