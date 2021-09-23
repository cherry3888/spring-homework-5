package ru.cherry.springhomework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cherry.springhomework.dao.AuthorDao;
import ru.cherry.springhomework.dao.AuthorDaoJdbc;
import ru.cherry.springhomework.dao.BookDao;
import ru.cherry.springhomework.dao.BookDaoJdbc;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoTest {

	@Autowired
	private BookDao bookDao;

	@Test
	@DirtiesContext
	void getAllBooks() {
		List<Book> books = bookDao.getAll();
		assertEquals(3, books.size());
	}

	@Test
	@DirtiesContext
	void insertBook() {
		Book book = new Book("Book-4", new Author(1L, "Author-1"), new Genre(1L, "Genre-1"));
		bookDao.insert(book);
		List<Book> books = bookDao.getByTitle(book.getTitle());
		assertTrue(books.size() == 1);
	}

	@Test
	@DirtiesContext
	void updateBook() {
		Book book = bookDao.getById(1L);
		book.setTitle("Book-123");
		bookDao.update(book);
		Book book1 = bookDao.getById(1L);
		assertEquals(book.getTitle(), book1.getTitle());
	}

	@Test
	@DirtiesContext
	void deleteBook() {
		Book book = bookDao.getById(1L);
		bookDao.delete(book);
		Book book1 = bookDao.getById(1L);
		assertNull(book1);
	}

	@Test
	@DirtiesContext
	void getById() {
		Book book = bookDao.getById(2L);
		assertNotNull(book);
	}

	@Test
	@DirtiesContext
	void getByTitle() {
		List<Book> books = bookDao.getByTitle("Book-2");
		assertTrue(books.size() == 1);
	}

}