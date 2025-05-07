package org.bea.db.dao;

import org.bea.db.entity.ParagraphEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ParagraphDaoJdbc extends BaseDao<ParagraphEntity> implements ParagraphDao{

    private final static String TABLE_NAME = "paragraphs";

    public ParagraphDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public void delete(UUID id, String byColumn) {
        super.delete(id, byColumn, TABLE_NAME);
    }

    @Override
    public ParagraphEntity setIdAndInsert(ParagraphEntity paragraphEntity) {
        return super.setIdAndInsert(paragraphEntity, TABLE_NAME);
    }
}
