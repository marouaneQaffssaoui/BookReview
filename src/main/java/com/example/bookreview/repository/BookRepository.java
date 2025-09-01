package com.example.bookreview.repository;

import com.example.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenre(com.example.bookreview.model.Genre genre);
}
