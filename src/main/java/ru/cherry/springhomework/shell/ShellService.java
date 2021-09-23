package ru.cherry.springhomework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.cherry.springhomework.service.AuthorService;
import ru.cherry.springhomework.service.BookService;
import ru.cherry.springhomework.service.GenreService;

@ShellComponent
public class ShellService {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public ShellService(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Get all books", key = {"gab", "get books"})
    public void gatAllBooks() {
        bookService.getAllBooks();
    }

    @ShellMethod(value = "Add book", key = {"adb", "add book"})
    public void addBook() {
        bookService.addBook();
    }

    @ShellMethod(value = "Find book", key = {"fb", "find book"})
    public void findBook() {
        bookService.findBook();
    }

    @ShellMethod(value = "Edit book", key = {"eb", "edit book"})
    public void editBook() {
        bookService.editBook();
    }

    @ShellMethod(value = "Delete book", key = {"db", "delete book"})
    public void deleteBook() {
      bookService.deleteBook();
    }

    @ShellMethod(value = "Get all authors", key = {"gaa", "get authors"})
    public void getAllAuthors() {
        authorService.getAllAuthors();
    }

    @ShellMethod(value = "Add author", key = {"aa", "add author"})
    public void addAuthor() {
        authorService.addAuthor();
    }

    @ShellMethod(value = "Get author", key = {"ga", "get author"})
    public void getAuthor() {
        authorService.getAuthor();
    }

    @ShellMethod(value = "Edit author", key = {"ea", "edit author"})
    public void editAuthor() {
        authorService.editAuthor();
    }

    @ShellMethod(value = "Delete author", key = {"da", "delete author"})
    public void deleteAuthor() {
        authorService.deleteAuthor();
    }


    @ShellMethod(value = "Get genres", key = {"gag", "get genres"})
    public void getAllGenres() {
        genreService.getAllGenres();
    }

    @ShellMethod(value = "Add genre", key = {"ag", "add genre"})
    public void addGenre() {
        genreService.addGenre();
    }

    @ShellMethod(value = "Get genre", key = {"gg", "get genre"})
    public void getGenre() {
        genreService.getGenre();
    }

    @ShellMethod(value = "Edit genre", key = {"eg", "edit genre"})
    public void editGenre() {
        genreService.editGenre();
    }

    @ShellMethod(value = "Delete genre", key = {"dg", "delete genre"})
    public void deleteGenre() {
        genreService.deleteGenre();
    }

}
