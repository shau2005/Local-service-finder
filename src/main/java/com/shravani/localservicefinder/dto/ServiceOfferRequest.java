package com.shravani.localservicefinder.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ServiceOfferRequest {

    @NotNull(message = "Service request id is required")
    private Long serviceRequestId;

    @NotNull(message = "Provider id is required")
    private Long providerId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "1.0", message = "Price must be at least 1")
    private BigDecimal price;

    @Size(max = 500, message = "Message cannot exceed 500 characters")
    private String message;

    public ServiceOfferRequest() {
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
