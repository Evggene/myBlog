package org.bea;

import org.bea.config.DataTestConfiguration;
import org.bea.configuration.DataSourceConfiguration;
import org.bea.model.Post;
import org.bea.db.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class, DataTestConfiguration.class})
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void save() {
        var post = createPostWithoutId();
        var postSaved = postRepository.save(post);
        var postInDb = postRepository.getById(postSaved.getId());
        Assertions.assertNotNull(postInDb);
    }

    private Post createPostWithoutId() {
        var post = new Post();
        post.setTitle("random title");
        post.setContent("random content");
        post.setTextPreview("random preview");
        return post;
    }

}
