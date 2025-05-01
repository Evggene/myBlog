package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentRepository;
import org.bea.db.dao.LikeRepository;
import org.bea.db.dao.PostRepository;
import org.bea.db.dao.TagRepository;
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
public class EditPostHandler {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    public void editPost(UUID id, String title, String text, String tags, String fileName) {
        var postEdited = buildPostWithId(id, title, text, fileName);
        postRepository.update(postEdited);
        // найти теги
        // вычесть
        // записать оставшиеся, если остались
    }

    private Post buildPostWithId(UUID id, String title, String text, String fileName) {
        return Post.builder()
                .id(id)
                .title(title)
                .imagePath(fileName)
                .textPreview(text)
                .text(text)
                .build();
    }

}

