package com.example.bookreview.controller;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Genre;
import com.example.bookreview.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Book> addBook(@PathVariable Long userId, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(userId, book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Optional<Book> book = bookService.findBook(id);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable Genre genre) {
        return ResponseEntity.ok(bookService.findBooksByGenre(genre));
    }
}
