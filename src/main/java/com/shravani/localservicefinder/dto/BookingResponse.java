package com.shravani.localservicefinder.dto;

import com.shravani.localservicefinder.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingResponse {

    private Long id;
    private Long serviceRequestId;
    private String requestTitle;
    private Long serviceOfferId;
    private Long userId;
    private String userName;
    private Long providerId;
    private String providerName;
    private BigDecimal price;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private BookingStatus status;
    private LocalDateTime createdAt;

    public BookingResponse() {
    }

    public BookingResponse(Long id, Long serviceRequestId, String requestTitle, Long serviceOfferId,
                           Long userId, String userName, Long providerId, String providerName,
                           BigDecimal price, LocalDate scheduledDate, LocalTime scheduledTime,
                           BookingStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.serviceRequestId = serviceRequestId;
        this.requestTitle = requestTitle;
        this.serviceOfferId = serviceOfferId;
        this.userId = userId;
        this.userName = userName;
        this.providerId = providerId;
        this.providerName = providerName;
        this.price = price;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public Long getServiceOfferId() {
        return serviceOfferId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public void setServiceOfferId(Long serviceOfferId) {
        this.serviceOfferId = serviceOfferId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public void setScheduledTime(LocalTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
