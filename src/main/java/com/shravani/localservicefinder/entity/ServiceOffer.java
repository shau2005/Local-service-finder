package com.shravani.localservicefinder.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_offers")
public class ServiceOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false)
    private ServiceRequest serviceRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceOfferStatus status = ServiceOfferStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ServiceOffer() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = ServiceOfferStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public User getProvider() {
        return provider;
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

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public void setProvider(User provider) {
        this.provider = provider;
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
