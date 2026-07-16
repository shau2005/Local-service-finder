package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.ReviewRequest;
import com.shravani.localservicefinder.dto.ReviewResponse;
import com.shravani.localservicefinder.entity.Booking;
import com.shravani.localservicefinder.entity.BookingStatus;
import com.shravani.localservicefinder.entity.Review;
import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.BookingRepository;
import com.shravani.localservicefinder.repository.ReviewRepository;
import com.shravani.localservicefinder.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookingRepository bookingRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + request.getBookingId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new IllegalArgumentException("Review can be given only after booking is COMPLETED");
        }

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Only the user who booked the service can give review");
        }

        if (reviewRepository.existsByBookingId(booking.getId())) {
            throw new IllegalArgumentException("Review already exists for this booking");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setUser(user);
        review.setProvider(booking.getProvider());
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        return convertToResponse(review);
    }

    public ReviewResponse getReviewByBooking(Long bookingId) {
        Review review = reviewRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for booking id: " + bookingId));

        return convertToResponse(review);
    }

    public List<ReviewResponse> getReviewsByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return reviewRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ReviewResponse> getReviewsByProvider(Long providerId) {

        if (!userRepository.existsById(providerId)) {
            throw new ResourceNotFoundException("Provider not found with id: " + providerId);
        }

        return reviewRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ReviewResponse convertToResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getBooking().getId(),
                review.getUser().getId(),
                review.getUser().getName(),
                review.getProvider().getId(),
                review.getProvider().getName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
