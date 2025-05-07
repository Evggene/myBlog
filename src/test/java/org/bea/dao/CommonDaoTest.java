package org.bea.dao;


import org.bea.db.dao.CommentDao;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CommonDaoTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected ParagraphDao paragraphDao;
    @Autowired
    protected PostDao postDao;
    @Autowired
    protected LikeDao likeDao;
    @Autowired
    protected CommentDao commentDao;
    @Autowired
    protected TagDao tagDao;

}
