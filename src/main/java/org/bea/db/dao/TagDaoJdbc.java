package org.bea.db.dao;

import org.bea.model.Tag;
import org.bea.model.TagsToPost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TagDaoJdbc extends BaseDao<Tag> implements TagDao {

    private final static String TABLE_NAME = "tags";
    private final static String LINK_TABLE_NAME = "tags_to_post";

    public TagDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Tag setIdAndInsert(Tag tag) {
        return super.setIdAndInsert(tag, TABLE_NAME);
    }

    @Override
    public void insert(TagsToPost tagsToPost) {
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(LINK_TABLE_NAME);
        var paramSource = new BeanPropertySqlParameterSource(tagsToPost);
        insert.execute(paramSource);
    }

    @Override
    public void deleteLinkTagsToPost(UUID id) {
        super.delete(id, "post_id", LINK_TABLE_NAME);
    }
}
