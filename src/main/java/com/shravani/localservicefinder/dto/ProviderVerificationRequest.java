package com.shravani.localservicefinder.dto;

import jakarta.validation.constraints.NotNull;

public class ProviderVerificationRequest {

    @NotNull(message = "Admin id is required")
    private Long adminId;

    @NotNull(message = "Verification status is required")
    private Boolean verified;

    public ProviderVerificationRequest() {
    }

    public Long getAdminId() {
        return adminId;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}