package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.entity.Like;
import org.bea.db.entity.Paragraph;
import org.bea.db.entity.Post;
import org.bea.db.entity.Tag;
import org.bea.db.entity.TagsToPost;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AddPostHandler extends CommonHandler{

    public AddPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao) {
        super(tagDao, paragraphDao, postDao, likeDao);
    }

    public void addPost(String title, String text, String tagsRaw, String originalFilename) {
        var tagsCreated = super.handleTags(tagsRaw);
        var paragraphs = splitText(text);
        var post = handlePost(title, paragraphs[0], originalFilename);
        super.handleParagraphs(paragraphs, post.getId());
        handleLike(post);
        super.handlePostsTags(post.getId(), tagsCreated);
    }

    private String[] splitText(String text) {
        return text.split("\n");
    }

    private Post handlePost(String title, String preview, String originalFilename) {
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

    private void handleLike(Post post) {
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

