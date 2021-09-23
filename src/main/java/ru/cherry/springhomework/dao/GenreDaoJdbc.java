package ru.cherry.springhomework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.cherry.springhomework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbcOperations;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from genre", Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", genre.getName());
        namedParameterJdbcOperations.update("insert into genre (name) values (:name)", params);
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", genre.getName());
        params.put("id", genre.getId());
        namedParameterJdbcOperations.update("update genre set name = (:name) where id = (:id)", params);
    }

    @Override
    public void delete(Genre genre) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", genre.getId());
        namedParameterJdbcOperations.update("delete from genre where id = :id", params);
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject("select * from genre where id = :id", params, new GenreMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        try {
            return namedParameterJdbcOperations.queryForObject("select * from genre where upper(name) = upper(:name)", params, new GenreMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genre", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

}
