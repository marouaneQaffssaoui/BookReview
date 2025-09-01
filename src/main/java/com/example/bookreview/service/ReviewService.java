package com.example.bookreview.service;

import com.example.bookreview.model.Book;
import com.example.bookreview.model.Review;
import com.example.bookreview.model.User;
import com.example.bookreview.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository,
                         BookService bookService,
                         UserService userService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Review addReview(Long bookId, Long userId, Review review) {
        Book book = bookService.findBook(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userService.findUser(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        review.setBook(book);
        review.setUser(user);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public double getAverageRating(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
