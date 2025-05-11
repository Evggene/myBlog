package org.bea.config;


import org.bea.MainTest;
import org.bea.db.dao.CommentDao;
import org.bea.db.dao.LikeDao;
import org.bea.db.dao.ParagraphDao;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.dao.TagsToPostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CommonDaoTest extends MainTest {

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
    @Autowired
    protected TagsToPostDao tagsToPostDao;

}
