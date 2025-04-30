package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.configuration.ResourceRootPathConfiguration;
import org.bea.db.repository.LikeRepository;
import org.bea.db.repository.PostRepository;
import org.bea.db.repository.TagRepository;
import org.bea.model.Like;
import org.bea.model.Post;
import org.bea.model.Tag;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddPostHandler {

    private final ResourceRootPathConfiguration rootPath;
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

    public void copyImageToResources(MultipartFile image) {
        if (image.getOriginalFilename().isBlank()) {
            return;
        }
        try {
            var path = Path.of(
                    rootPath.getRootPathTo(ResourceRootPathConfiguration.IMAGES)
                            + File.separator
                            + image.getOriginalFilename());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

