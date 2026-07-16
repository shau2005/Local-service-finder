package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.ProviderProfileRequest;
import com.shravani.localservicefinder.dto.ProviderProfileResponse;
import com.shravani.localservicefinder.dto.ProviderVerificationRequest;
import com.shravani.localservicefinder.service.ProviderProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider-profiles")
public class ProviderProfileController {

    private final ProviderProfileService providerProfileService;

    public ProviderProfileController(ProviderProfileService providerProfileService) {
        this.providerProfileService = providerProfileService;
    }

    @PostMapping
    public ResponseEntity<ProviderProfileResponse> createProviderProfile(
            @Valid @RequestBody ProviderProfileRequest request) {

        ProviderProfileResponse savedProfile = providerProfileService.createProviderProfile(request);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfileResponse>> getAllProviderProfiles() {
        List<ProviderProfileResponse> providerProfiles = providerProfileService.getAllProviderProfiles();
        return ResponseEntity.ok(providerProfiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderProfileResponse> getProviderProfileById(@PathVariable Long id) {
        ProviderProfileResponse providerProfile = providerProfileService.getProviderProfileById(id);
        return ResponseEntity.ok(providerProfile);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProviderProfileResponse>> getProviderProfilesByCategory(@PathVariable Long categoryId) {
        List<ProviderProfileResponse> providerProfiles = providerProfileService.getProviderProfilesByCategory(categoryId);
        return ResponseEntity.ok(providerProfiles);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ProviderProfileResponse>> getProviderProfilesByCity(@PathVariable String city) {
        List<ProviderProfileResponse> providerProfiles = providerProfileService.getProviderProfilesByCity(city);
        return ResponseEntity.ok(providerProfiles);
    }

    @PatchMapping("/{profileId}/verify")
    public ResponseEntity<ProviderProfileResponse> verifyProviderProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProviderVerificationRequest request) {

        ProviderProfileResponse updatedProfile =
                providerProfileService.verifyProviderProfile(profileId, request);

        return ResponseEntity.ok(updatedProfile);
    }
}