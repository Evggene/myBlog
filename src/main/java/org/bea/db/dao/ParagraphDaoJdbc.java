package org.bea.db.dao;

import org.bea.db.entity.Paragraph;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ParagraphDaoJdbc extends BaseDao<Paragraph> implements ParagraphDao{

    private final static String TABLE_NAME = "paragraphs";

    public ParagraphDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }


    @Override
    public void update(Paragraph comment) {

    }

    @Override
    public void delete(UUID id) {
        super.delete(id, "id", TABLE_NAME);
    }

    @Override
    public Paragraph setIdAndInsert(Paragraph paragraph) {
        return super.setIdAndInsert(paragraph, TABLE_NAME);
    }
}
