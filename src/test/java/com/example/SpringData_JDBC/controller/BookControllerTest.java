package com.example.SpringData_JDBC.controller;

import com.example.SpringData_JDBC.data.DatabaseSeeder;
import com.example.SpringData_JDBC.model.Book;
import com.example.SpringData_JDBC.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DatabaseSeeder databaseSeeder;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setPublicationYear(2024);
    }

    @Test
    public void getAllBooks_ReturnsBooksList() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Sample Book"))
                .andExpect(jsonPath("$[0].author").value("Sample Author"))
                .andExpect(jsonPath("$[0].publicationYear").value(2024));
    }

    @Test
    public void getBookById_ExistingId_ReturnsBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(book);

        mockMvc.perform(get("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.author").value("Sample Author"))
                .andExpect(jsonPath("$.publicationYear").value(2024));
    }

    @Test
    public void createBook_ValidBook_ReturnsCreatedBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.author").value("Sample Author"))
                .andExpect(jsonPath("$.publicationYear").value(2024));
    }

    @Test
    public void updateBook_ExistingId_ValidBook_ReturnsUpdatedBook() throws Exception {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.author").value("Sample Author"))
                .andExpect(jsonPath("$.publicationYear").value(2024));
    }

    @Test
    public void deleteBook_ExistingId_ReturnsDeletedBookId() throws Exception {
        Long bookId = 1L;
        when(bookService.deleteBook(bookId)).thenReturn(Math.toIntExact(bookId));

        mockMvc.perform(delete("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bookId.toString()));
    }
}
