package com.example.SpringData_JDBC.service;

import com.example.SpringData_JDBC.exception.BookNotFoundException;
import com.example.SpringData_JDBC.model.Book;
import com.example.SpringData_JDBC.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setPublicationYear(2024);
    }

    @Test
    void getAllBooks_ReturnsBooksList() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<Book> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }

    @Test
    void getBook_ExistingId_ReturnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(1L);
        assertNotNull(result);
        assertEquals(book, result);
    }

    @Test
    void getBook_NonExistingId_BookNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBook(1L));
    }

    @Test
    void createBook_ValidBook_ReturnsCreatedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.createBook(book);
        assertNotNull(result);
        assertEquals(book, result);
    }

    @Test
    void updateBook_ExistingId_ValidBook_ReturnsUpdatedBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);
        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
    }

    @Test
    void updateBook_NonExistingId_BookNotFoundException() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, updatedBook));
    }

    @Test
    void deleteBook_ExistingId_ReturnsDeletedBookId() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(1L);

        int result = bookService.deleteBook(1L);
        assertEquals(1, result);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NonExistingId_BookNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
