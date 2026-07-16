package com.shravani.localservicefinder.dto;

import com.shravani.localservicefinder.entity.ServiceOfferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServiceOfferResponse {

    private Long id;
    private Long serviceRequestId;
    private String requestTitle;
    private Long providerId;
    private String providerName;
    private BigDecimal price;
    private String message;
    private ServiceOfferStatus status;
    private LocalDateTime createdAt;

    public ServiceOfferResponse() {
    }

    public ServiceOfferResponse(Long id, Long serviceRequestId, String requestTitle,
                                Long providerId, String providerName, BigDecimal price,
                                String message, ServiceOfferStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.serviceRequestId = serviceRequestId;
        this.requestTitle = requestTitle;
        this.providerId = providerId;
        this.providerName = providerName;
        this.price = price;
        this.message = message;
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

    public Long getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public ServiceOfferStatus getStatus() {
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

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(ServiceOfferStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
