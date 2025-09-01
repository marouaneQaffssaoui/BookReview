package com.example.bookreview.UnitTest;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.ReviewRepository;
import com.example.bookreview.service.BookService;
import com.example.bookreview.service.ReviewService;
import com.example.bookreview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewService reviewService;

    private User user;
    private Book book;
    private Review review;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFname("John");

        book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAddedBy(user);

        review = new Review();
        review.setRating(5);
        review.setComment("Excellent!");
    }

    @Test
    void testAddReview() {
        when(userService.findUser(1L)).thenReturn(Optional.ofNullable(user));
        when(bookService.findBook(1L)).thenReturn(Optional.ofNullable(book));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review saved = reviewService.addReview(1L, 1L, review);
        assertNotNull(saved);
        assertEquals(5, saved.getRating());
        verify(reviewRepository, times(1)).save(review);
    }
}
