package org.bea.db.dao;

import org.bea.db.entity.TagEntity;
import org.bea.db.entity.TagsToPostLink;
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
import java.util.stream.Collectors;

@Repository
public class TagDaoJdbc extends BaseDao<TagEntity> implements TagDao {

    private final static String TABLE_NAME = "tags";
    private final static String LINK_TABLE_NAME = "tags_to_post";

    BeanPropertyRowMapper<TagEntity> tagRowMapper = new BeanPropertyRowMapper<>(TagEntity.class);

    public TagDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public TagEntity setIdAndInsert(TagEntity tagEntity) {
        return super.setIdAndInsert(tagEntity, TABLE_NAME);
    }

    @Override
    public void createLinkTagToPost(TagsToPostLink tagsToPostLink) {
        tagsToPostLink.setId(UUID.randomUUID());
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(LINK_TABLE_NAME);
        var paramSource = new BeanPropertySqlParameterSource(tagsToPostLink);
        insert.execute(paramSource);
    }

    @Override
    public void deleteLinkTagsToPost(UUID id, String byColumn) {
        super.delete(id, byColumn, LINK_TABLE_NAME);
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
    public long countPostsByTags(List<TagEntity> tagEntities) {
        var tagIdsForSql = tagEntities.stream()
                .map(TagEntity::getId)
                .collect(Collectors.toSet());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tagIds", tagIdsForSql);
        return namedParameterJdbcTemplate.queryForObject(
                "select count(distinct post_id) from "
                        + LINK_TABLE_NAME
                        + " where tag_id in (:tagIds) and deleted_at is null;",
                paramMap, Long.class);
    }
}
