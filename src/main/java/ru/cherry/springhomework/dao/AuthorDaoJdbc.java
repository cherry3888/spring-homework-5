package ru.cherry.springhomework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbcOperations;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from author", Integer.class);
    }

    @Override
    public void insert(Author author) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", author.getName());
        namedParameterJdbcOperations.update("insert into author (name) values (:name)", params);
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", author.getName());
        params.put("id", author.getId());
        namedParameterJdbcOperations.update("update author set name = (:name) where id = (:id)", params);
    }

    @Override
    public void delete(Author author) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", author.getId());
        namedParameterJdbcOperations.update("delete from author where id = :id", params);
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject("select * from author where id = :id", params, new AuthorMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        try {
            return namedParameterJdbcOperations.queryForObject("select * from author where upper(name) = upper(:name)", params, new AuthorMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name from author", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

}
