package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.ServiceRequestRequest;
import com.shravani.localservicefinder.dto.ServiceRequestResponse;
import com.shravani.localservicefinder.entity.Category;
import com.shravani.localservicefinder.entity.ServiceRequest;
import com.shravani.localservicefinder.entity.ServiceRequestStatus;
import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.entity.UserRole;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.CategoryRepository;
import com.shravani.localservicefinder.repository.ServiceRequestRepository;
import com.shravani.localservicefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository,
                                 UserRepository userRepository,
                                 CategoryRepository categoryRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public ServiceRequestResponse createServiceRequest(ServiceRequestRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        if (user.getRole() != UserRole.USER) {
            throw new IllegalArgumentException("Only users with USER role can create service request");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setUser(user);
        serviceRequest.setCategory(category);
        serviceRequest.setTitle(request.getTitle());
        serviceRequest.setDescription(request.getDescription());
        serviceRequest.setAddressLine(request.getAddressLine());
        serviceRequest.setArea(request.getArea());
        serviceRequest.setCity(request.getCity());
        serviceRequest.setPincode(request.getPincode());
        serviceRequest.setLandmark(request.getLandmark());
        serviceRequest.setPreferredDate(request.getPreferredDate());
        serviceRequest.setPreferredTime(request.getPreferredTime());
        serviceRequest.setStatus(ServiceRequestStatus.OPEN);

        ServiceRequest savedRequest = serviceRequestRepository.save(serviceRequest);

        return convertToResponse(savedRequest);
    }

    public List<ServiceRequestResponse> getAllServiceRequests() {
        return serviceRequestRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ServiceRequestResponse getServiceRequestById(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

        return convertToResponse(serviceRequest);
    }

    public List<ServiceRequestResponse> getServiceRequestsByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return serviceRequestRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ServiceRequestResponse> getOpenServiceRequests() {
        return serviceRequestRepository.findByStatus(ServiceRequestStatus.OPEN)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ServiceRequestResponse> getOpenRequestsByCategoryAndCity(Long categoryId, String city) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return serviceRequestRepository
                .findByCategoryIdAndCityIgnoreCaseAndStatus(categoryId, city, ServiceRequestStatus.OPEN)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ServiceRequestResponse convertToResponse(ServiceRequest serviceRequest) {
        return new ServiceRequestResponse(
                serviceRequest.getId(),
                serviceRequest.getUser().getId(),
                serviceRequest.getUser().getName(),
                serviceRequest.getCategory().getId(),
                serviceRequest.getCategory().getName(),
                serviceRequest.getTitle(),
                serviceRequest.getDescription(),
                serviceRequest.getAddressLine(),
                serviceRequest.getArea(),
                serviceRequest.getCity(),
                serviceRequest.getPincode(),
                serviceRequest.getLandmark(),
                serviceRequest.getPreferredDate(),
                serviceRequest.getPreferredTime(),
                serviceRequest.getStatus(),
                serviceRequest.getCreatedAt()
        );
    }
}