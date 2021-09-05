package ru.cherry.springhomework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.cherry.springhomework.dao.AuthorDao;
import ru.cherry.springhomework.dao.BookDao;
import ru.cherry.springhomework.dao.GenreDao;
import ru.cherry.springhomework.service.BookService;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class);
    }
}
