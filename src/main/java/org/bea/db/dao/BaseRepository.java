package org.bea.db.dao;

import lombok.RequiredArgsConstructor;
import org.bea.model.UUIDModel;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public abstract class BaseRepository<T> {

    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public T setIdAndInsert(T entity, String tableName) {
        ((UUIDModel)entity).setId(UUID.randomUUID());
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
        var paramSource = new BeanPropertySqlParameterSource(entity);
        try {
            insert.execute(paramSource);
        } catch (DuplicateKeyException e) {
            // ignore
        }
        return entity;
    }

    public T insert(T entity, String tableName) {
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
        var paramSource = new BeanPropertySqlParameterSource(entity);
        try {
            insert.execute(paramSource);
        } catch (DuplicateKeyException e) {
            // ignore
        }
        return entity;
    }

    void delete(UUID id, String idName, String tableName) {
        var sql = "UPDATE " + tableName + " SET deleted_at = :deletedAt WHERE " + idName + " = :id ";
        var params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("deletedAt", Instant.now(Clock.systemUTC()));
        namedParameterJdbcTemplate.update(sql, params);
    }
}
