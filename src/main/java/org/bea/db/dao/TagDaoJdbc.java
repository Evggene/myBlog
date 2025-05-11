package org.bea.db.dao;

import org.bea.db.entity.TagEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class TagDaoJdbc extends BaseDao<TagEntity> implements TagDao {

    private final static String TABLE_NAME = "tags";

    BeanPropertyRowMapper<TagEntity> tagRowMapper = new BeanPropertyRowMapper<>(TagEntity.class);

    public TagDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public TagEntity setIdAndInsert(TagEntity tagEntity) {
        return super.setIdAndInsert(tagEntity, TABLE_NAME);
    }

    @Override
    public TagEntity findByName(String name) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        var tag = namedParameterJdbcTemplate.query("select * from tags t where t.name = :name", paramMap, tagRowMapper);
        if (tag.isEmpty()) {
            return null;
        }
        return tag.getFirst();
    }
    @Override
    public TagEntity findById(UUID id) {
        return super.findById(id, TagEntity.class, TABLE_NAME);
    }
}
