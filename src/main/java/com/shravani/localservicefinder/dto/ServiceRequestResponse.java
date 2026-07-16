package com.shravani.localservicefinder.dto;

import com.shravani.localservicefinder.entity.ServiceRequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServiceRequestResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String description;
    private String addressLine;
    private String area;
    private String city;
    private String pincode;
    private String landmark;
    private LocalDate preferredDate;
    private LocalTime preferredTime;
    private ServiceRequestStatus status;
    private LocalDateTime createdAt;

    public ServiceRequestResponse() {
    }

    public ServiceRequestResponse(Long id, Long userId, String userName, Long categoryId, String categoryName,
                                  String title, String description, String addressLine, String area, String city,
                                  String pincode, String landmark, LocalDate preferredDate, LocalTime preferredTime,
                                  ServiceRequestStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.title = title;
        this.description = description;
        this.addressLine = addressLine;
        this.area = area;
        this.city = city;
        this.pincode = pincode;
        this.landmark = landmark;
        this.preferredDate = preferredDate;
        this.preferredTime = preferredTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public LocalTime getPreferredTime() {
        return preferredTime;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public void setPreferredTime(LocalTime preferredTime) {
        this.preferredTime = preferredTime;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
