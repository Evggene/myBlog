package org.bea.db.repository;

import lombok.RequiredArgsConstructor;
import org.bea.model.Tag;
import org.bea.model.UUIDModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public abstract class BaseRepository<T> {

    protected final JdbcTemplate jdbcTemplate;

    public T save(T entity, String tableName) {
        ((UUIDModel)entity).setId(UUID.randomUUID());
        var insert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
        var paramSource = new BeanPropertySqlParameterSource(entity);
        try {
            insert.execute(paramSource);
        } catch (Exception e) {
            System.out.println(e);
        }
        return entity;
    }
}
