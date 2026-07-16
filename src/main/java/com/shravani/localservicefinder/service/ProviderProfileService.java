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
            throw new IllegalArgumentException("Only PROVIDER users can create provider profile");
        }

        if (providerProfileRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("Provider profile already exists for this user");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        ProviderProfile providerProfile = new ProviderProfile();
        providerProfile.setUser(user);
        providerProfile.setCategory(category);
        providerProfile.setExperienceYears(request.getExperienceYears());
        providerProfile.setBaseArea(request.getBaseArea());
        providerProfile.setCity(request.getCity());
        providerProfile.setPincode(request.getPincode());
        providerProfile.setServiceRadiusKm(request.getServiceRadiusKm());
        providerProfile.setVerified(false);

        ProviderProfile savedProfile = providerProfileRepository.save(providerProfile);

        return convertToResponse(savedProfile);
    }

    public List<ProviderProfileResponse> getAllProviderProfiles() {
        return providerProfileRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ProviderProfileResponse getProviderProfileById(Long id) {
        ProviderProfile providerProfile = providerProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found with id: " + id));

        return convertToResponse(providerProfile);
    }

    public List<ProviderProfileResponse> getProviderProfilesByCategory(Long categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

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
            throw new IllegalArgumentException("Only ADMIN can verify provider profile");
        }

        ProviderProfile providerProfile = providerProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found with id: " + profileId));

        providerProfile.setVerified(request.getVerified());

        ProviderProfile updatedProfile = providerProfileRepository.save(providerProfile);

        return convertToResponse(updatedProfile);
    }

    private ProviderProfileResponse convertToResponse(ProviderProfile providerProfile) {
        return new ProviderProfileResponse(
                providerProfile.getId(),
                providerProfile.getUser().getId(),
                providerProfile.getUser().getName(),
                providerProfile.getCategory().getId(),
                providerProfile.getCategory().getName(),
                providerProfile.getExperienceYears(),
                providerProfile.getBaseArea(),
                providerProfile.getCity(),
                providerProfile.getPincode(),
                providerProfile.getServiceRadiusKm(),
                providerProfile.getVerified(),
                providerProfile.getCreatedAt()
        );
    }
}