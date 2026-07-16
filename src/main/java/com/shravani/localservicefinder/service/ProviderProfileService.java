package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.ProviderProfileRequest;
import com.shravani.localservicefinder.dto.ProviderProfileResponse;
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

    public ProviderProfileService(ProviderProfileRepository providerProfileRepository,
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
            throw new IllegalArgumentException("Only users with PROVIDER role can create provider profile");
        }

        if (providerProfileRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("Provider profile already exists for user id: " + request.getUserId());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

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

    public List<ProviderProfileResponse> getProvidersByCategory(Long categoryId) {
        return providerProfileRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ProviderProfileResponse> getProvidersByCity(String city) {
        return providerProfileRepository.findByCityIgnoreCase(city)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ProviderProfileResponse convertToResponse(ProviderProfile profile) {
        return new ProviderProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getUser().getName(),
                profile.getCategory().getId(),
                profile.getCategory().getName(),
                profile.getExperienceYears(),
                profile.getBaseArea(),
                profile.getCity(),
                profile.getPincode(),
                profile.getServiceRadiusKm(),
                profile.getVerified(),
                profile.getCreatedAt()
        );
    }
}