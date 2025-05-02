package org.bea.config;

import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.service.AddPostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    AddPostHandler addPostHandler(TagDao tagDao, ParagraphDao paragraphDao, PostDao postDao, LikeDao likeDao) {
        return new AddPostHandler(tagDao, paragraphDao,postDao, likeDao);
    }
}
