package com.example.SpringData_JDBC.repository;

import com.example.SpringData_JDBC.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("publication_year")
        ));
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("publication_year")
        )).stream().findFirst();
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            String sql = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());
        } else {
            String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
            jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());
        }
        return book;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
