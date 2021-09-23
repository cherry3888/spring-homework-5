package ru.cherry.springhomework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbcOperations;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from book", Integer.class);
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("author_id", book.getAuthor().getId());
        params.put("genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update("insert into book (genre_id, author_id, title) values (:genre_id, :author_id, :title)", params);
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("id", book.getId());
        namedParameterJdbcOperations.update("update book set title = (:title) where id = (:id)", params);
    }

    @Override
    public void delete(Book book) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        namedParameterJdbcOperations.update("delete from book where id = :id", params);
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select b.id, b.title, " +
                            "a.name as author_name, a.id as author_id, " +
                            "g.name as genre_name, g.id as genre_id " +
                            "from book b " +
                            "join author a on b.author_id = a.id " +
                            "join genre g on b.genre_id = g.id " +
                            "where b.id = :id", params, new BookMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Book> getByTitle(String title) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("title", title);
        try {
            return namedParameterJdbcOperations.query(
                    "select b.id, b.title, " +
                            "a.name as author_name, a.id as author_id, " +
                            "g.name as genre_name, g.id as genre_id " +
                            "from book b " +
                            "join author a on b.author_id = a.id " +
                            "join genre g on b.genre_id = g.id " +
                            "where upper(b.title) = upper(:title)", params, new BookMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<Book>();
        }
    }

    @Override
    public Book getByTitleAndAuthorIdAndGenreId(String title, long authorId, long genreId) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("authorId", authorId);
        params.put("genreId", genreId);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select b.id, b.title, " +
                            "a.name as author_name, a.id as author_id, " +
                            "g.name as genre_name, g.id as genre_id " +
                            "from book b " +
                            "join author a on b.author_id = a.id and a.id = :authorId " +
                            "join genre g on b.genre_id = g.id and g.id = :genreId " +
                            "where upper(b.title) = upper(:title)", params, new BookMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.title, " +
                "a.name as author_name, a.id as author_id, " +
                "g.name as genre_name, g.id as genre_id " +
                "from book b " +
                "join author a on b.author_id = a.id " +
                "join genre g on b.genre_id = g.id", new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String authorName = resultSet.getString("author_name");
            long authorId = resultSet.getLong("author_id");
            String genreName = resultSet.getString("genre_name");
            long genreId = resultSet.getLong("genre_id");
            Author author = new Author(authorId, authorName);
            Genre genre = new Genre(genreId, genreName);
            return new Book(id, title, author, genre);
        }
    }

}
