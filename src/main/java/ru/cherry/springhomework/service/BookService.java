package ru.cherry.springhomework.service;

import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

public interface BookService {
    void getAllBooks();
    void addBook();
    void findBook();
    void editBook();
    void deleteBook();
}
