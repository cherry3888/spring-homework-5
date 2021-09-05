package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.dao.GenreDao;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;
    private final MessageService messageService;

    public GenreServiceImpl(GenreDao genreDao, MessageService messageService) {
        this.genreDao = genreDao;
        this.messageService = messageService;
    }

    @Override
    public void getAllGenres() {
        List<Genre> genres = genreDao.getAll();
        if (!CollectionUtils.isEmpty(genres)) {
            messageService.sendMessage("Жанры:");
            genres.forEach(genre -> messageService.sendMessage(genre.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @Override
    public void addGenre() {
        messageService.sendMessage("Введите жанр:");
        String genreName = messageService.getMessage();
        Genre genre = genreDao.getByName(genreName);
        if (null == genre) {
            genreDao.insert(new Genre(genreName));
            messageService.sendMessage("Жанр " + genreName + " успешно сохранен.");
        } else {
            messageService.sendMessage("Такой жанр уже существует.");
        }
    }

    @Override
    public void getGenre() {
        messageService.sendMessage("Введите жанр:");
        String genreName = messageService.getMessage();
        Genre genre = genreDao.getByName(genreName);
        if (null == genre) {
            messageService.sendMessage("Жанр не найден.");
        } else {
            messageService.sendMessage(genre.toString());
        }
    }

    @Override
    public void editGenre() {
        messageService.sendMessage("Введите название жанра:");
        Long id = messageService.getLongMessage();
        Genre genre = genreDao.getById(id);
        if (null != genre) {
            messageService.sendMessage("Введите новое название жанра:");
            String newName = messageService.getMessage();
            genre.setName(newName);
            genreDao.update(genre);
            messageService.sendMessage("Жанр успешно сохранен.");
        } else {
            messageService.sendMessage("Жанр не найден!");
        }
    }

    @Override
    public void deleteGenre() {
        messageService.sendMessage("Введите идентификатор жанра:");
        Long id = messageService.getLongMessage();
        Genre genre = genreDao.getById(id);
        if (null != genre) {
            genreDao.delete(genre);
            messageService.sendMessage("Жанр удален.");
        } else {
            messageService.sendMessage("Жанр не найден!");
        }
    }
}
