package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
import org.bea.model.Post;
import org.bea.model.Tag;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class TagRepositoryJdbcImpl extends BaseRepository<Tag> implements TagRepository {

    private final static String TABLE_NAME = "tags";

    public TagRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Tag save(Tag tag) {
        return super.save(tag, TABLE_NAME);
    }
}
