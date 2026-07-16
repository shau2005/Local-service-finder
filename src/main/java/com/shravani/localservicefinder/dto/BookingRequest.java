package com.shravani.localservicefinder.dto;

import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Service offer id is required")
    private Long serviceOfferId;

    public BookingRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getServiceOfferId() {
        return serviceOfferId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setServiceOfferId(Long serviceOfferId) {
        this.serviceOfferId = serviceOfferId;
    }
}