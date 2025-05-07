//package org.bea.dao;
//
//import org.bea.db.entity.Paragraph;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//public class ParagraphDaoTest extends CommonDaoTest {
//
//    private final static UUID initialParagraphId = UUID.fromString("30000000-0000-0000-0000-000000000001");
//    private final static UUID postId = UUID.fromString("20000000-0000-0000-0000-000000000001");
//
//    @BeforeEach
//    void setUp() {
//        jdbcTemplate.execute("DELETE FROM paragraphs");
//        jdbcTemplate.execute("DELETE FROM posts");
//
//        jdbcTemplate.execute(
//                """
//                INSERT INTO posts(id, title, text_preview, image_path)
//                    VALUES
//                ('20000000-0000-0000-0000-000000000001',
//                'Test Post',
//                'Test Preview',
//                'test.png');
//                """);
//
//        jdbcTemplate.execute(
//                """
//                INSERT INTO paragraphs(id, post_id, ord, text)
//                    VALUES
//                ('30000000-0000-0000-0000-000000000001',
//                '20000000-0000-0000-0000-000000000001',
//                1,
//                'Initial paragraph text');
//                """);
//    }
//
//    @Test
//    void setIdAndInsertTest() {
//        var paragraph = createParagraphWithoutId(postId);
//        paragraphDao.setIdAndInsert(paragraph);
//
//        var postAgg = postAggregateRepository.findByIdFullMode(postId);
//        Assertions.assertNotNull(postAgg);
//        Assertions.assertNotNull(postAgg.getTextParts());
//        Assertions.assertEquals(2, postAgg.getTextParts().length);
//        Assertions.assertEquals("Initial paragraph text", postAgg.getTextParts()[0]);
//        Assertions.assertEquals("Test paragraph text", postAgg.getTextParts()[1]);
//    }
//
//    /**
//     * К созданному посту с одним параграфом добавляем второй
//     * удаляем его, затем удаляем первоначальный параграф
//     */
//    @Test
//    void deleteTest() {
//        var paragraphRaw = createParagraphWithoutId(postId);
//        var secondParagraph = paragraphDao.setIdAndInsert(paragraphRaw);
//
//        var postAgg = postAggregateRepository.findByIdFullMode(postId);
//        Assertions.assertEquals(2, postAgg.getTextParts().length);
//
//        paragraphDao.delete(secondParagraph.getId(), "id");
//
//        var postAggWithDeletedParagraph = postAggregateRepository.findByIdFullMode(postId);
//        Assertions.assertEquals("Initial paragraph text", postAggWithDeletedParagraph.getTextParts()[0]);
//
//        paragraphDao.delete(initialParagraphId, "id");
//
//        var postAggWithoutParagraph = postAggregateRepository.findByIdFullMode(postId);
//        Assertions.assertNull(postAggWithoutParagraph.getTextParts()[0]);
//
//    }
//
//    private Paragraph createParagraphWithoutId(UUID id) {
//        var paragraph = new Paragraph();
//        paragraph.setPostId(id);
//        paragraph.setOrd(2);
//        paragraph.setText("Test paragraph text");
//        return paragraph;
//    }
//}
