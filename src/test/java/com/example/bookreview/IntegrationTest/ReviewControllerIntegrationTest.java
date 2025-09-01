package com.example.bookreview.IntegrationTest;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Genre;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;
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
public class ReviewControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private User user;
    private Book book;

    @BeforeEach
    void setup() {
        reviewRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setFname("John");
        user.setLname("Doe");
        user.setEmail("JohnDoe@example.com");
        user.setPassword("1234");
        user = userRepository.save(user);

        book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setGenre(Genre.OTHER);
        book.setAddedBy(user);
        book = bookRepository.save(book);
    }

    /*@Test
    void testAddReview() throws Exception {
        Review review = new Review();
        review.setUser(user);
        review.setRating(5);
        review.setComment("Excellent book!");

        mockMvc.perform(post("/books/" + book.getId() + "/reviews/user/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Excellent book!"));
    }*/

    @Test
    void testGetReviewsForBook() throws Exception {
        Review review = new Review();
        review.setRating(5);
        review.setComment("Great!");
        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);

        mockMvc.perform(get("/books/" + book.getId() + "/reviews"))
                .andExpect(status().isOk());
    }
}
