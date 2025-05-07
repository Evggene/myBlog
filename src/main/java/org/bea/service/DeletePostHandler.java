package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.dao.TagsToPostDao;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePostHandler {

    private final PostDao postDao;
    private final TagDao tagDao;
    private final CommentDao commentDao;
    private final ParagraphDao paragraphDao;
    private final TagsToPostDao tagsToPostDao;

    public void deletePost(UUID id) {
        postDao.delete(id);
        tagsToPostDao.deleteLinkTagsToPost(id, "post_id");
        commentDao.delete(id, "post_id");
        paragraphDao.delete(id, "post_id");
    }
}

