package org.bea.dao;

import org.bea.db.entity.ParagraphEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ParagraphDaoTest extends CommonDaoTest {

    private final static UUID initialParagraphId = UUID.fromString("30000000-0000-0000-0000-000000000001");
    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM paragraphs");
        jdbcTemplate.execute("DELETE FROM posts");

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

        var postAgg = paragraphDao.findListById(postId);
        Assertions.assertNotNull(postAgg);
        Assertions.assertEquals(2, postAgg.size());
    }

    /**
     * К созданному посту с одним параграфом добавляем второй
     * удаляем его, затем удаляем первоначальный параграф
     */
    @Test
    void deleteTest() {
        var paragraphRaw = createParagraphWithoutId(postId);
        var secondParagraph = paragraphDao.setIdAndInsert(paragraphRaw);

        var paragraphs = paragraphDao.findListById(postId);
        Assertions.assertEquals(2, paragraphs.size());

        paragraphDao.delete(secondParagraph.getId(), "id");

        var firstParahraoh = paragraphDao.findListById(postId);
        Assertions.assertEquals(1, firstParahraoh.size());

        paragraphDao.delete(postId, "post_id");

        var deleted = commentDao.findListById(postId);
        Assertions.assertEquals(0, deleted.size());

    }

    private ParagraphEntity createParagraphWithoutId(UUID id) {
        var paragraph = new ParagraphEntity();
        paragraph.setPostId(id);
        paragraph.setOrd(2);
        paragraph.setText("Test paragraph text");
        return paragraph;
    }
}
