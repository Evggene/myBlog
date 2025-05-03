package org.bea.service;

import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.entity.Like;
import org.bea.db.entity.Post;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AddPostHandler extends CommonHandler{

    public AddPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao) {
        super(tagDao, paragraphDao, postDao, likeDao);
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

    private Post buildPostAndInsert(String title, String preview, String originalFilename) {
        var post = buildPost(title, preview, originalFilename);
        postDao.setIdAndInsert(post);
        return post;
    }

    private Post buildPost(String title, String preview, String originalFilename) {
        return Post.builder()
                .title(title)
                .imagePath(originalFilename)
                .textPreview(preview)
                .build();
    }

    private void buildLikeAndInsert(Post post) {
        var like = buildLike(post);
        likeDao.createForPost(like);
    }

    private Like buildLike(Post post) {
        return Like.builder()
                .postId(post.getId())
                .likesCount(0)
                .build();
    }
}

