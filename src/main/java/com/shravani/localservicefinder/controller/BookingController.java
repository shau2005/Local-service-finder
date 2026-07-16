package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.BookingRequest;
import com.shravani.localservicefinder.dto.BookingResponse;
import com.shravani.localservicefinder.dto.BookingStatusUpdateRequest;
import com.shravani.localservicefinder.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        BookingResponse savedBooking = bookingService.createBooking(request);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Long userId) {
        List<BookingResponse> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByProvider(@PathVariable Long providerId) {
        List<BookingResponse> bookings = bookingService.getBookingsByProvider(providerId);
        return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @PathVariable Long id,
            @Valid @RequestBody BookingStatusUpdateRequest request) {

        BookingResponse updatedBooking = bookingService.updateBookingStatus(id, request);
        return ResponseEntity.ok(updatedBooking);
    }
}