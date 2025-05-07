package org.bea.service;

import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.dao.TagsToPostDao;
import org.bea.db.entity.LikeEntity;
import org.bea.db.entity.PostEntity;
import org.springframework.stereotype.Service;

@Service
public class AddPostHandler extends CommonHandler{


    public AddPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao, TagsToPostDao tagsToPostDao) {
        super(tagDao, paragraphDao, postDao, likeDao, tagsToPostDao);
    }

    public void addPost(String title, String text, String tagsRaw, String originalFilename) {
        var tagsCreated = super.prepareTagsAndInsertNew(tagsRaw);
        var paragraphs = splitTextToParagraphs(text);
        var post = buildPostAndInsert(title, paragraphs[0].strip(), originalFilename);
        super.buildParagraphsAndInsert(paragraphs, post.getId());
        buildLikeAndInsert(post);
        super.buildLinkTagsToPostAndInsert(post.getId(), tagsCreated);
    }

    private String[] splitTextToParagraphs(String text) {
        return text.split("\n");
    }

    private PostEntity buildPostAndInsert(String title, String preview, String originalFilename) {
        var post = buildPost(title, preview, originalFilename);
        postDao.setIdAndInsert(post);
        return post;
    }

    private PostEntity buildPost(String title, String preview, String originalFilename) {
        return PostEntity.builder()
                .title(title)
                .imagePath(originalFilename)
                .textPreview(preview)
                .build();
    }

    private void buildLikeAndInsert(PostEntity post) {
        var like = buildLike(post);
        likeDao.createForPost(like);
    }

    private LikeEntity buildLike(PostEntity post) {
        return LikeEntity.builder()
                .postId(post.getId())
                .likesCount(0)
                .build();
    }
}

