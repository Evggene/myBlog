package org.bea.repository;

import lombok.RequiredArgsConstructor;
import org.bea.model.Post;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryJdbcImpl implements PostRepository{

    private final JdbcTemplate jdbcTemplate;
    private final static String TABLE_NAME = "post";
    private final static String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    @Override
    public List<Post> findAll() {
        var rowMapper = new BeanPropertyRowMapper<>(Post.class);
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }
}
