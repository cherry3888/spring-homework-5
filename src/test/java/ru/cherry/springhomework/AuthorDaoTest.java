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
import ru.cherry.springhomework.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoTest {

	@Autowired
	private AuthorDao authorDao;

	@Test
	@DirtiesContext
	void getAllAuthors() {
		List<Author> authors = authorDao.getAll();
		assertEquals(3, authors.size());
	}

	@Test
	@DirtiesContext
	void insertAuthor() {
		Author author = new Author(4L,"Author-4");
		authorDao.insert(author);
		Author author1 = authorDao.getByName(author.getName());
		assertNotNull(author1);
	}

	@Test
	@DirtiesContext
	void updateAuthor() {
		Author author = authorDao.getById(3L);
		author.setName("Author-123");
		authorDao.update(author);
		Author author1 = authorDao.getByName(author.getName());
		assertNotNull(author1);
	}

	@Test
	@DirtiesContext
	void deleteAuthor() {
		Author author = authorDao.getById(1L);
		authorDao.delete(author);
		Author author1 = authorDao.getById(1L);
		assertNull(author1);
	}

	@Test
	@DirtiesContext
	void getById() {
		Author author = authorDao.getById(2L);
		assertNotNull(author);
	}

	@Test
	@DirtiesContext
	void getByName() {
		Author author = authorDao.getByName("Author-1");
		assertNotNull(author);
	}

}