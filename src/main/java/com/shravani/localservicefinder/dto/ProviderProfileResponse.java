package com.shravani.localservicefinder.dto;

import java.time.LocalDateTime;

public class ProviderProfileResponse {

    private Long id;
    private Long userId;
    private String providerName;
    private Long categoryId;
    private String categoryName;
    private Integer experienceYears;
    private String baseArea;
    private String city;
    private String pincode;
    private Integer serviceRadiusKm;
    private Boolean verified;
    private LocalDateTime createdAt;

    public ProviderProfileResponse() {
    }

    public ProviderProfileResponse(Long id, Long userId, String providerName, Long categoryId, String categoryName,
                                   Integer experienceYears, String baseArea, String city, String pincode,
                                   Integer serviceRadiusKm, Boolean verified, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.providerName = providerName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.experienceYears = experienceYears;
        this.baseArea = baseArea;
        this.city = city;
        this.pincode = pincode;
        this.serviceRadiusKm = serviceRadiusKm;
        this.verified = verified;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProviderName() {
        return providerName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public String getBaseArea() {
        return baseArea;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public Integer getServiceRadiusKm() {
        return serviceRadiusKm;
    }

    public Boolean getVerified() {
        return verified;
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

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setBaseArea(String baseArea) {
        this.baseArea = baseArea;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setServiceRadiusKm(Integer serviceRadiusKm) {
        this.serviceRadiusKm = serviceRadiusKm;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}