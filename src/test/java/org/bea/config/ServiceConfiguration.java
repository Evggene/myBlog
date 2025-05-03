package org.bea.config;

import org.bea.db.dao.CommentDao;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.service.AddPostHandler;
import org.bea.service.DeletePostHandler;
import org.bea.service.EditPostHandler;
import org.bea.service.FindPostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    AddPostHandler addPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao) {
        return new AddPostHandler(tagDao, paragraphDao,postDao, likeDao);
    }

    @Bean
    DeletePostHandler deletePostHandler(PostDao postDao, TagDao tagDao, CommentDao commentDao, ParagraphDao paragraphDao) {
        return new DeletePostHandler(postDao, tagDao, commentDao, paragraphDao);
    }

    @Bean
    EditPostHandler editPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao) {
        return new EditPostHandler(tagDao, paragraphDao, postDao, likeDao);
    }

    @Bean
    FindPostHandler findPostHandler(
            PostAggregateRepository postAggregateRepository, PostDao postDao, TagDao tagDao) {
        return new FindPostHandler(postAggregateRepository, postDao, tagDao);
    }
}
