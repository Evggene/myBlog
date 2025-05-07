package org.bea.db.dao;

import lombok.RequiredArgsConstructor;
import org.bea.db.entity.AuditFields;
import org.bea.db.entity.PostEntity;
import org.bea.db.entity.UUIDEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public abstract class BaseDao<T extends AuditFields & UUIDEntity> {

    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected T setIdAndInsert(T entity, String tableName) {
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MICROS));
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
        var paramSource = new BeanPropertySqlParameterSource(entity);
        try {
            insert.execute(paramSource);
        } catch (DuplicateKeyException e) {
            // do nothing
        }
        return entity;
    }

    protected void delete(UUID id, String idName, String tableName) {
        var sql = "UPDATE " + tableName + " SET deleted_at = :deletedAt WHERE " + idName + " = :id ";
        var params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("deletedAt", Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MICROS));
        namedParameterJdbcTemplate.update(sql, params);
    }

    protected void setUpdatedAt(StringBuilder sql, MapSqlParameterSource params) {
        sql.append(" updatedAt = :updatedAt ");
        params.addValue("updatedAt", Instant.now(Clock.systemUTC()));
    }

    protected T findById(UUID id, Class<T> clazz, String tableName) {
        var rowMapper = new BeanPropertyRowMapper<>(clazz);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        var res = namedParameterJdbcTemplate.query(
                "select * from " + tableName + " WHERE id = :COLUMN_ID and deleted_at is null", paramMap, rowMapper);
        if (!res.isEmpty()) {
            return res.getFirst();
        }
        return null;
    }

    protected List<T> findListById(UUID id, String columnName, Class<T> clazz, String tableName) {
        var rowMapper = new BeanPropertyRowMapper<>(clazz);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("COLUMN_ID", id);
        return namedParameterJdbcTemplate.query(
                "select * from " + tableName + " WHERE " + columnName + "  = :COLUMN_ID and deleted_at is null", paramMap, rowMapper);
    }
}
