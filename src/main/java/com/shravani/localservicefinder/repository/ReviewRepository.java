package com.shravani.localservicefinder.repository;

import com.shravani.localservicefinder.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByBookingId(Long bookingId);

    Optional<Review> findByBookingId(Long bookingId);

    List<Review> findByUserId(Long userId);

    List<Review> findByProviderId(Long providerId);
}