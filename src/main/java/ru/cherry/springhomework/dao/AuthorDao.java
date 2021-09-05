package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;

import java.util.List;

public interface AuthorDao {

    int count();

    void insert(Author Author);

    void update(Author Author);

    void delete(Author author);

    Author getById(Long id);

    Author getByName(String name);

    List<Author> getAll();
}
