package org.bea.service;

import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.entity.Post;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditPostHandler extends CommonHandler {

    public EditPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao) {
        super(tagDao, paragraphDao, postDao, likeDao);
    }

    public void editPost(UUID id, String title, String text, String tags, String fileName) {
        var paragraphs = text.split("\n");
        paragraphDao.delete(id, "post_id");
        super.buildParagraphsAndInsert(paragraphs, id);

        var postEdited = buildPostWithId(id, title, paragraphs[0], fileName);
        postDao.update(postEdited);

        var tagsToLink = super.prepareTagsAndInsertNew(tags);
        tagDao.deleteLinkTagsToPost(postEdited.getId(), "post_id");
        super.buildLinkTagsToPostAndInsert(postEdited.getId(), tagsToLink);
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

