package ru.cherry.springhomework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.dao.AuthorDao;
import ru.cherry.springhomework.dao.BookDao;
import ru.cherry.springhomework.dao.GenreDao;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final MessageService messageService;

    @Override
    public void getAllBooks() {
        List<Book> books = bookDao.getAll();
        if (!CollectionUtils.isEmpty(books)) {
            messageService.sendMessage("Книги:");
            books.forEach(book -> messageService.sendMessage(book.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @Override
    public void addBook() {
        messageService.sendMessage("Введите название книги:");
        String title = messageService.getMessage();

        messageService.sendMessage("Введите автора:");
        String authorName = messageService.getMessage();
        Author author = authorDao.getByName(authorName);
        if (null == author) {
            authorDao.insert(new Author(authorName));
            author = authorDao.getByName(authorName);
        }

        messageService.sendMessage("Введите жанр:");
        String genreName = messageService.getMessage();
        Genre genre = genreDao.getByName(genreName);
        if (null == genre) {
            genreDao.insert(new Genre(genreName));
            genre = genreDao.getByName(genreName);
        }

        Book book = bookDao.getByTitleAndAuthorIdAndGenreId(title, author.getId(), genre.getId());
        if (null == book) {
            bookDao.insert(new Book(title, author, genre));
            book = bookDao.getByTitleAndAuthorIdAndGenreId(title, author.getId(), genre.getId());
            messageService.sendMessage("Книга успешно сохранена.");
        } else {
            messageService.sendMessage("Такая книга уже существует.");
        }
        messageService.sendMessage(book.toString());

    }

    @Override
    public void findBook() {
        messageService.sendMessage("Введите название книги:");
        String title = messageService.getMessage();
        List<Book> books = bookDao.getByTitle(title);
        if (!CollectionUtils.isEmpty(books)) {
            messageService.sendMessage("Результат поиска:");
            books.forEach(book -> messageService.sendMessage(book.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @Override
    public void editBook() {
        messageService.sendMessage("Введите идентификатор книги:");
        Long id = messageService.getLongMessage();
        Book book = bookDao.getById(id);
        if (null != book) {
            messageService.sendMessage("Введите новое название книги:");
            String newTitle = messageService.getMessage();
            book.setTitle(newTitle);
            bookDao.update(book);
            messageService.sendMessage("Книга успешно сохранена.");
        } else {
            messageService.sendMessage("Книга не найдена!");
        }
    }

    @Override
    public void deleteBook() {
        messageService.sendMessage("Введите идентификатор книги:");
        Long id = messageService.getLongMessage();
        Book book = bookDao.getById(id);
        if (null != book) {
            bookDao.delete(book);
            messageService.sendMessage("Книга успешно удалена. Больше вы её не увидите.");
        } else {
            messageService.sendMessage("Книга не найдена!");
        }
    }
}
