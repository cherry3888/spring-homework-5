package ru.cherry.springhomework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Genre {
    public Genre(String name) {
        this.name = name;
    }

    private long id;
    private String name;
}
