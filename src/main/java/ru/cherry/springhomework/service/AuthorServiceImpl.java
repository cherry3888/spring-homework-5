package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.dao.AuthorDao;
import ru.cherry.springhomework.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final MessageService messageService;

    public AuthorServiceImpl(AuthorDao authorDao, MessageService messageService) {
        this.authorDao = authorDao;
        this.messageService = messageService;
    }

    @Override
    public void getAllAuthors() {
        List<Author> authors = authorDao.getAll();
        if (!CollectionUtils.isEmpty(authors)) {
            messageService.sendMessage("Авторы:");
            authors.forEach(author -> messageService.sendMessage(author.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @Override
    public void addAuthor() {
        messageService.sendMessage("Введите автора:");
        String authorName = messageService.getMessage();
        Author author = authorDao.getByName(authorName);
        if (null == author) {
            authorDao.insert(new Author(authorName));
            messageService.sendMessage("Автор " + authorName + " успешно сохранен.");
        } else {
            messageService.sendMessage("Такой автор уже существует.");
        }
    }

    @Override
    public void getAuthor() {
        messageService.sendMessage("Введите автора:");
        String authorName = messageService.getMessage();
        Author author = authorDao.getByName(authorName);
        if (null == author) {
            messageService.sendMessage("Автор не найден.");
        } else {
            messageService.sendMessage(author.toString());
        }
    }

    @Override
    public void editAuthor() {
        messageService.sendMessage("Введите идентификатор автора:");
        Long id = messageService.getLongMessage();
        Author author = authorDao.getById(id);
        if (null != author) {
            messageService.sendMessage("Введите новое имя автора:");
            String newName = messageService.getMessage();
            author.setName(newName);
            authorDao.update(author);
            messageService.sendMessage("Автор успешно сохранен.");
        } else {
            messageService.sendMessage("Автор не найден!");
        }
    }

    @Override
    public void deleteAuthor() {
        messageService.sendMessage("Введите идентификатор автора:");
        Long id = messageService.getLongMessage();
        Author author = authorDao.getById(id);
        if (null != author) {
            authorDao.delete(author);
            messageService.sendMessage("Автор удален.");
        } else {
            messageService.sendMessage("Автор не найден!");
        }
    }

}
