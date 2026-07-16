package com.shravani.localservicefinder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProviderProfileRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Experience years is required")
    @Min(value = 0, message = "Experience years cannot be negative")
    private Integer experienceYears;

    @NotBlank(message = "Base area is required")
    @Size(max = 100, message = "Base area cannot exceed 100 characters")
    private String baseArea;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @NotBlank(message = "Pincode is required")
    @Size(max = 10, message = "Pincode cannot exceed 10 characters")
    private String pincode;

    @NotNull(message = "Service radius is required")
    @Min(value = 1, message = "Service radius must be at least 1 km")
    private Integer serviceRadiusKm;

    public ProviderProfileRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategoryId() {
        return categoryId;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
}
