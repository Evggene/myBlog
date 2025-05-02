package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.CommentDao;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.model.Post;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EditPostHandler {

    private final PostDao postDao;

    public void editPost(UUID id, String title, String text, String tags, String fileName) {
        var postEdited = buildPostWithId(id, title, text, fileName);
        postDao.update(postEdited);
        //todo: доделать
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
                .build();
    }

}

