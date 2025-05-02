package org.bea.dao;

import org.bea.db.entity.Paragraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParagraphDaoTest extends CommonDaoContext {

    private final static UUID initialParagraphId = UUID.fromString("30000000-0000-0000-0000-000000000001");
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
        var paragraph = createParagraphWithoutId(postId);
        paragraphDao.setIdAndInsert(paragraph);

        var postAgg = postAggregateRepository.findById(postId);
        Assertions.assertNotNull(postAgg);
        Assertions.assertNotNull(postAgg.getTextParts());
        Assertions.assertEquals(2, postAgg.getTextParts().length);
        Assertions.assertEquals("Initial paragraph text", postAgg.getTextParts()[0]);
        Assertions.assertEquals("Test paragraph text", postAgg.getTextParts()[1]);
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

    /**
     * К созданному посту с одним параграфом добавляем второй
     * удаляем его, затем удаляем первоначальный параграф
     */
    @Test
    void deleteTest() {
        var paragraphRaw = createParagraphWithoutId(postId);
        var secondParagraph = paragraphDao.setIdAndInsert(paragraphRaw);

        var postAgg = postAggregateRepository.findById(postId);
        Assertions.assertEquals(2, postAgg.getTextParts().length);

        paragraphDao.delete(secondParagraph.getId());

        var postAggWithDeletedParagraph = postAggregateRepository.findById(postId);
        Assertions.assertEquals("Initial paragraph text", postAggWithDeletedParagraph.getTextParts()[0]);

        paragraphDao.delete(initialParagraphId);

        var postAggWithoutParagraph = postAggregateRepository.findById(postId);
        Assertions.assertNull(postAggWithoutParagraph.getTextParts()[0]);

    }

    private Paragraph createParagraphWithoutId(UUID id) {
        var paragraph = new Paragraph();
        paragraph.setPostId(id);
        paragraph.setOrd(2);
        paragraph.setText("Test paragraph text");
        return paragraph;
    }
}
