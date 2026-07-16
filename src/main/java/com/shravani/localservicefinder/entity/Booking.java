package com.shravani.localservicefinder.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false, unique = true)
    private ServiceRequest serviceRequest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_offer_id", nullable = false, unique = true)
    private ServiceOffer serviceOffer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private LocalTime scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status = BookingStatus.CONFIRMED;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Booking() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = BookingStatus.CONFIRMED;
        }
    }

    public Long getId() {
        return id;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public ServiceOffer getServiceOffer() {
        return serviceOffer;
    }

    public User getUser() {
        return user;
    }

    public User getProvider() {
        return provider;
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

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public void setServiceOffer(ServiceOffer serviceOffer) {
        this.serviceOffer = serviceOffer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProvider(User provider) {
        this.provider = provider;
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
