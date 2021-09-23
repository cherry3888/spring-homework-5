package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    void insert(Genre genre);

    void update(Genre genre);

    void delete(Genre genre);

    Genre getById(Long id);

    Genre getByName(String name);

    List<Genre> getAll();

}
