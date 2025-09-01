package com.example.bookreview.UnitTest;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Genre;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.service.BookService;
import com.example.bookreview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookService bookService;

    private User user;
    private Book book;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFname("John");

        book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setGenre(Genre.OTHER);
    }

    @Test
    void testAddBook() {
        when(userService.findUser(1L)).thenReturn(Optional.ofNullable(user));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.addBook(1L, book);
        assertNotNull(savedBook);
        assertEquals("Clean Code", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> found = bookService.findBook(1L);
        assertNotNull(found.isPresent());
        assertEquals("Clean Code", found.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }
}
