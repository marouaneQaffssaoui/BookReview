package com.example.bookreview.service;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;

    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public Book addBook(Long userId, Book book) {
        User user = userService.findUser(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        book.setAddedBy(user);
        return bookRepository.save(book);
    }

    public Optional<Book> findBook(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> findBooksByGenre(com.example.bookreview.model.Genre genre) {
        return bookRepository.findByGenre(genre);
    }
}
