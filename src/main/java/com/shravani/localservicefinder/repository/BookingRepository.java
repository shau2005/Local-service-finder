package com.shravani.localservicefinder.repository;

import com.shravani.localservicefinder.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByProviderId(Long providerId);

    boolean existsByServiceRequestId(Long serviceRequestId);

    boolean existsByServiceOfferId(Long serviceOfferId);
}