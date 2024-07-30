package com.example.SpringData_JDBC.service;

import com.example.SpringData_JDBC.model.Book;

public interface BookService {
    Iterable<Book> getAllBooks();
    Book getBook(Long id);
    Book createBook(Book book);
    Book updateBook(Long id, Book book);
    int deleteBook(Long id);
}
