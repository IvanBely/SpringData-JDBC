package com.example.SpringData_JDBC.service;

import com.example.SpringData_JDBC.exception.BookNotFoundException;
import com.example.SpringData_JDBC.model.Book;
import com.example.SpringData_JDBC.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        book.setId(existingBook.getId());
        return bookRepository.save(book);
    }

    public int deleteBook(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        bookRepository.deleteById(id);
        return Math.toIntExact(id);
    }
}
