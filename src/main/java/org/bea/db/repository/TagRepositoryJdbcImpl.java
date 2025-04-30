package org.bea.db.repository;

import org.bea.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryJdbcImpl extends BaseRepository<Tag> implements TagRepository {

    private final static String TABLE_NAME = "tags";

    public TagRepositoryJdbcImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Tag setIdAndInsert(Tag tag) {
        return super.setIdAndInsert(tag, TABLE_NAME);
    }
}
