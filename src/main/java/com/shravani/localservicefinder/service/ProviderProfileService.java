package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.ProviderProfileRequest;
import com.shravani.localservicefinder.dto.ProviderProfileResponse;
import com.shravani.localservicefinder.dto.ProviderVerificationRequest;
import com.shravani.localservicefinder.entity.Category;
import com.shravani.localservicefinder.entity.ProviderProfile;
import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.entity.UserRole;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.CategoryRepository;
import com.shravani.localservicefinder.repository.ProviderProfileRepository;
import com.shravani.localservicefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderProfileService {

    private final ProviderProfileRepository providerProfileRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProviderProfileService(
            ProviderProfileRepository providerProfileRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository) {

        this.providerProfileRepository = providerProfileRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProviderProfileResponse createProviderProfile(ProviderProfileRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        if (user.getRole() != UserRole.PROVIDER) {
            throw new RuntimeException("Only PROVIDER users can create provider profile");
        }

        if (providerProfileRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("Provider profile already exists for user id: " + request.getUserId());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        if (!category.isActive()) {
            throw new RuntimeException("Cannot create provider profile for inactive category: " + category.getName());
        }

        ProviderProfile profile = new ProviderProfile();
        profile.setUser(user);
        profile.setCategory(category);
        profile.setExperienceYears(request.getExperienceYears());
        profile.setBaseArea(request.getBaseArea());
        profile.setCity(request.getCity());
        profile.setPincode(request.getPincode());
        profile.setServiceRadiusKm(request.getServiceRadiusKm());
        profile.setVerified(false);

        ProviderProfile savedProfile = providerProfileRepository.save(profile);
        return convertToResponse(savedProfile);
    }

    public List<ProviderProfileResponse> getAllProviderProfiles() {
        return providerProfileRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ProviderProfileResponse getProviderProfileById(Long id) {
        ProviderProfile profile = providerProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found with id: " + id));

        return convertToResponse(profile);
    }

    public List<ProviderProfileResponse> getProviderProfilesByCategory(Long categoryId) {
        return providerProfileRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ProviderProfileResponse> getProviderProfilesByCity(String city) {
        return providerProfileRepository.findByCityIgnoreCase(city)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ProviderProfileResponse verifyProviderProfile(Long profileId, ProviderVerificationRequest request) {
        User admin = userRepository.findById(request.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + request.getAdminId()));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Only ADMIN can verify provider profiles");
        }

        ProviderProfile profile = providerProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found with id: " + profileId));

        profile.setVerified(request.getVerified());

        ProviderProfile savedProfile = providerProfileRepository.save(profile);
        return convertToResponse(savedProfile);
    }

    private ProviderProfileResponse convertToResponse(ProviderProfile profile) {
        ProviderProfileResponse response = new ProviderProfileResponse();

        response.setId(profile.getId());
        response.setUserId(profile.getUser().getId());
        response.setProviderName(profile.getUser().getName());
        response.setCategoryId(profile.getCategory().getId());
        response.setCategoryName(profile.getCategory().getName());
        response.setExperienceYears(profile.getExperienceYears());
        response.setBaseArea(profile.getBaseArea());
        response.setCity(profile.getCity());
        response.setPincode(profile.getPincode());
        response.setServiceRadiusKm(profile.getServiceRadiusKm());
        response.setVerified(profile.getVerified());
        response.setCreatedAt(profile.getCreatedAt());

        return response;
    }
}