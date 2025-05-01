package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentDao;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.model.Like;
import org.bea.model.Post;
import org.bea.model.Tag;
import org.bea.model.TagsToPost;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddPostHandler {

    private final PostDao postDao;
    private final LikeDao likeDao;
    private final TagDao tagDao;

    public void addPost(String title, String text, String tags, String originalFilename) {
        var tagsCreated = handleTags(tags);
        var post = handlePost(title, text, originalFilename);
        handleLike(post);
        handlePostsTags(post.getId(), tagsCreated);
    }

    private void handlePostsTags(UUID id, List<Tag> tagsCreated) {
        var entities = tagsCreated.stream()
                .map(it -> buildTagsToPost(id, it))
                .collect(Collectors.toSet());
        entities.forEach(tagDao::insert);
    }

    private TagsToPost buildTagsToPost(UUID id, Tag it) {
        return TagsToPost.builder().postId(id).tagId(it.getId()).build();
    }

    private List<Tag> handleTags(String tags) {
        if (tags.isBlank()) {
            return Collections.emptyList();
        }
        var tagsUnique = convertToSet(tags);
        var tagsList = buildTags(tagsUnique);
        return tagsList.stream()
                .map(tagDao::setIdAndInsert)
                .toList();
    }

    private Post handlePost(String title, String text, String originalFilename) {
        var post = buildPost(title, text, originalFilename);
        postDao.setIdAndInsert(post);
        return post;
    }

    private void handleLike(Post post) {
        var like = buildLike(post);
        likeDao.createForPost(like);
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
                .text(text)
                .build();
    }

    private Like buildLike(Post post) {
        return Like.builder()
                .postId(post.getId())
                .likesCount(0)
                .build();
    }
}

