package ru.cherry.springhomework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cherry.springhomework.dao.GenreDao;
import ru.cherry.springhomework.dao.GenreDaoJdbc;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoTest {

	@Autowired
	private GenreDao genreDao;

	@Test
	@DirtiesContext
	void getAllGenres() {
		List<Genre> genres = genreDao.getAll();
		assertEquals(3, genres.size());
	}

	@Test
	@DirtiesContext
	void insertGenre() {
		Genre genre = new Genre("Позитив");
		genreDao.insert(genre);
		Genre genre1 = genreDao.getByName(genre.getName());
		assertNotNull(genre1);
	}

	@Test
	@DirtiesContext
	void updateGenre() {
		Genre genre = genreDao.getByName("Genre-2");
		genre.setName("Genre-123");
		genreDao.update(genre);
		Genre genre1 = genreDao.getByName(genre.getName());
		assertNotNull(genre1);
	}

	@Test
	@DirtiesContext
	void deleteGenre() {
		Genre genre = genreDao.getById(1L);
		genreDao.delete(genre);
		Genre genre1 = genreDao.getById(1L);
		assertNull(genre1);
	}

	@Test
	@DirtiesContext
	void getById() {
		Genre genre = genreDao.getById(2L);
		assertNotNull(genre);
	}

	@Test
	@DirtiesContext
	void getByName() {
		Genre genre = genreDao.getByName("Genre-1");
		assertNotNull(genre);
	}

}