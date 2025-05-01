package org.bea.db.dao;

import org.bea.model.Post;
import org.bea.model.Tag;
import org.bea.model.TagsToPost;
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
public class TagDaoJdbc extends BaseDao<Tag> implements TagDao {

    private final static String TABLE_NAME = "tags";
    private final static String LINK_TABLE_NAME = "tags_to_post";

    BeanPropertyRowMapper<Tag> tagRowMapper = new BeanPropertyRowMapper<>(Tag.class);

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

    @Override
    public Tag findByName(String name) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        var tag = namedParameterJdbcTemplate.query("select * from tags t where t.name = :name", paramMap, tagRowMapper);
        if (tag.isEmpty()) {
            return null;
        }
        return tag.get(0);
    }

    @Override
    public long countPostsByTags(List<Tag> tags) {
        var tagIdsForSql = tags.stream()
                .map(it -> it.getId().toString())
                .collect(Collectors.joining(","));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tagIds", tagIdsForSql);
        return namedParameterJdbcTemplate.queryForObject(
                "select count(*) from " + LINK_TABLE_NAME + " where tag_id in (:tagIds);", paramMap, Long.class);
    }
}
