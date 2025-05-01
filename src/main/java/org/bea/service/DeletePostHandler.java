package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentRepository;
import org.bea.db.dao.LikeRepository;
import org.bea.db.dao.PostRepository;
import org.bea.db.dao.TagRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePostHandler {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    public void deletePost(UUID id) {
        postRepository.delete(id);
        tagRepository.deleteLinkTagsToPost(id);
        commentRepository.delete(id, "post_id");
    }
}

