package com.shravani.localservicefinder.dto;

import com.shravani.localservicefinder.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;

public class BookingStatusUpdateRequest {

    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    public BookingStatusUpdateRequest() {
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
