package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.ProviderProfileRequest;
import com.shravani.localservicefinder.dto.ProviderProfileResponse;
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
    public ResponseEntity<ProviderProfileResponse> createProviderProfile(@Valid @RequestBody ProviderProfileRequest request) {
        ProviderProfileResponse savedProfile = providerProfileService.createProviderProfile(request);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProviderProfileResponse>> getAllProviderProfiles() {
        List<ProviderProfileResponse> profiles = providerProfileService.getAllProviderProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderProfileResponse> getProviderProfileById(@PathVariable Long id) {
        ProviderProfileResponse profile = providerProfileService.getProviderProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProviderProfileResponse>> getProvidersByCategory(@PathVariable Long categoryId) {
        List<ProviderProfileResponse> profiles = providerProfileService.getProvidersByCategory(categoryId);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ProviderProfileResponse>> getProvidersByCity(@PathVariable String city) {
        List<ProviderProfileResponse> profiles = providerProfileService.getProvidersByCity(city);
        return ResponseEntity.ok(profiles);
    }
}