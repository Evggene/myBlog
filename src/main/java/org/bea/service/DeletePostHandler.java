package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePostHandler {

    private final PostDao postDao;
    private final TagDao tagDao;
    private final CommentDao commentDao;

    public void deletePost(UUID id) {
        postDao.delete(id);
        tagDao.deleteLinkTagsToPost(id);
        commentDao.delete(id, "post_id");
    }
}

