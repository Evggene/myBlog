package org.bea.repository;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.RepositoryConfiguration;
import org.bea.db.dao.PostDao;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.model.Paragraph;
import org.bea.db.dao.ParagraphDao;
import org.bea.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfigurationTest.class, RepositoryConfiguration.class})
public class ParagraphDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ParagraphDao paragraphDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostAggregateRepository postAggregateRepository;

    private final static UUID id = UUID.fromString("30000000-0000-0000-0000-000000000001");
    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM paragraphs");
        jdbcTemplate.execute("DELETE FROM posts");

        jdbcTemplate.execute(
                """
                INSERT INTO posts(id, title, text_preview, image_path)
                    VALUES
                ('20000000-0000-0000-0000-000000000001', 
                'Test Post', 
                'Test Preview', 
                'test.png');
                """);

        jdbcTemplate.execute(
                """
                INSERT INTO paragraphs(id, post_id, ord, text) 
                    VALUES
                ('30000000-0000-0000-0000-000000000001', 
                '20000000-0000-0000-0000-000000000001', 
                1, 
                'Initial paragraph text');
                """);
    }

    @Test
    void setIdAndInsertTest() {
        var post = postDao.setIdAndInsert(createPostWithoutId());
        var paragraph = createParagraphWithoutId(post.getId());
        paragraphDao.setIdAndInsert(paragraph);

        var postAgg = postAggregateRepository.findById(post.getId());
        Assertions.assertNotNull(postAgg);
        Assertions.assertNotNull(postAgg.getTextParts());
        Assertions.assertEquals(1, postAgg.getTextParts().length);
        Assertions.assertEquals("Test paragraph text", postAgg.getTextParts()[0]);
    }

    //todo: доделать
//    @Test
//    void updateTest() {
//        var newText = "Updated paragraph text";
//        var newOrd = 2;
//        var paragraphRaw = createParagraphWithoutId();
//
//        var paragraph = paragraphDao.setIdAndInsert(paragraphRaw);
//        paragraph.setText(newText);
//        paragraph.setOrd(newOrd);
//
//        paragraphDao.update(paragraph);
//        var paragraphEdited = paragraphDao.findById(paragraph.getId());
//
//        assertEquals(newText, paragraphEdited.getText());
//        assertEquals(newOrd, paragraphEdited.getOrd());
//    }

    @Test
    void deleteTest() {
        var post = postDao.setIdAndInsert(createPostWithoutId());
        var paragraphRaw = createParagraphWithoutId(post.getId());
        var paragraph = paragraphDao.setIdAndInsert(paragraphRaw);

        paragraphDao.delete(paragraph.getId());

        var postAgg = postAggregateRepository.findById(post.getId());
        Assertions.assertNotNull(postAgg);
        Assertions.assertNull(postAgg.getTextParts()[0]);

    }

    private Paragraph createParagraphWithoutId(UUID id) {
        var paragraph = new Paragraph();
        paragraph.setPostId(id);
        paragraph.setOrd(1);
        paragraph.setText("Test paragraph text");
        return paragraph;
    }

    private Post createPostWithoutId() {
        var post = new Post();
        post.setTitle("random title");
        post.setTextPreview("random preview");
        post.setImagePath("");
        return post;
    }
}
