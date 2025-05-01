package org.bea.repository;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.RepositoryConfiguration;
import org.bea.model.Post;
import org.bea.db.dao.PostDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfigurationTest.class, RepositoryConfiguration.class})
public class PostDaoTest {

    @Autowired
    private PostDao postDao;

    @Test
    void save() {
        var post = createPostWithoutId();
        var postSaved = postDao.setIdAndInsert(post);
        var postInDb = postDao.findById(postSaved.getId());
        Assertions.assertNotNull(postInDb);
    }

    private Post createPostWithoutId() {
        var post = new Post();
        post.setTitle("random title");
        post.setText("random content");
        post.setTextPreview("random preview");
        return post;
    }

}
