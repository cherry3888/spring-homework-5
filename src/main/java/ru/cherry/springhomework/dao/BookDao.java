package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Book;
import java.util.List;

public interface BookDao {

    int count();

    void insert(Book Book);

    void update(Book Book);

    void delete(Book book);

    Book getById(Long id);

    List<Book> getByTitle(String title);

    Book getByTitleAndAuthorIdAndGenreId(String title, long authorId, long genreId);

    List<Book> getAll();
}
