package org.bea.db.dao;

import org.bea.db.entity.TagEntity;
import org.bea.db.entity.TagsToPostEntity;
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
public class TagsToPostDaoJdbc extends BaseDao<TagsToPostEntity> implements TagsToPostDao {

    private final static String TABLE_NAME = "tags_to_post";

    public TagsToPostDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public TagsToPostEntity createLinkTagToPost(TagsToPostEntity tagsToPostEntity) {
        return super.setIdAndInsert(tagsToPostEntity, TABLE_NAME);
    }

    @Override
    public void deleteLinkTagsToPost(UUID id, String byColumn) {
        super.delete(id, byColumn, TABLE_NAME);
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
                        + TABLE_NAME
                        + " where tag_id in (:tagIds) and deleted_at is null;",
                paramMap, Long.class);
    }

    @Override
    public TagsToPostEntity findById(UUID id) {
        return super.findById(id, TagsToPostEntity.class, TABLE_NAME);
    }

}
