package com.example.SpringData_JDBC.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final JdbcTemplate jdbcTemplate;

    public void createTables() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "author VARCHAR(255) NOT NULL, " +
                "publication_year INTEGER)");
    }

    public void insertData() {
        jdbcTemplate.update("INSERT INTO books(title, author, publication_year) VALUES(?, ?, ?)",
                "Book1", "Author1", 2021);
        jdbcTemplate.update("INSERT INTO books(title, author, publication_year) VALUES(?, ?, ?)",
                "Book2", "Author2", 2020);
        jdbcTemplate.update("INSERT INTO books(title, author, publication_year) VALUES(?, ?, ?)",
                "Book3", "Author3", 2019);
    }
}
