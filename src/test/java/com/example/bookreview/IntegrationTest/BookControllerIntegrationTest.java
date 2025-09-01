package com.example.bookreview.IntegrationTest;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Genre;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private User user;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setFname("John");
        user.setLname("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("1234");
        user = userRepository.save(user);
    }

    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setTitle("Clean Architecture");
        book.setAuthor("Robert C. Martin");
        book.setGenre(Genre.OTHER);

        mockMvc.perform(post("/books/user/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Architecture"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.genre").value("OTHER"));
    }

    @Test
    void testGetBooksByGenre() throws Exception {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setGenre(Genre.OTHER);
        book1.setAddedBy(user);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setGenre(Genre.BIOGRAPHY);
        book2.setAddedBy(user);
        bookRepository.save(book2);

        mockMvc.perform(get("/books/genre/OTHER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].genre").value("OTHER"));
    }
}
