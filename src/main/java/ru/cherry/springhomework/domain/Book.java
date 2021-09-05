package ru.cherry.springhomework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Book {
    private long id;
    private String title;
    private Author author;
    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}
