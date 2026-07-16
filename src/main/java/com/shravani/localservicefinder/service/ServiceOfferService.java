package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.ServiceOfferRequest;
import com.shravani.localservicefinder.dto.ServiceOfferResponse;
import com.shravani.localservicefinder.entity.ProviderProfile;
import com.shravani.localservicefinder.entity.ServiceOffer;
import com.shravani.localservicefinder.entity.ServiceOfferStatus;
import com.shravani.localservicefinder.entity.ServiceRequest;
import com.shravani.localservicefinder.entity.ServiceRequestStatus;
import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.entity.UserRole;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.ProviderProfileRepository;
import com.shravani.localservicefinder.repository.ServiceOfferRepository;
import com.shravani.localservicefinder.repository.ServiceRequestRepository;
import com.shravani.localservicefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOfferService {

    private final ServiceOfferRepository serviceOfferRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final ProviderProfileRepository providerProfileRepository;

    public ServiceOfferService(ServiceOfferRepository serviceOfferRepository,
                               ServiceRequestRepository serviceRequestRepository,
                               UserRepository userRepository,
                               ProviderProfileRepository providerProfileRepository) {
        this.serviceOfferRepository = serviceOfferRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.providerProfileRepository = providerProfileRepository;
    }

    public ServiceOfferResponse createServiceOffer(ServiceOfferRequest request) {

        ServiceRequest serviceRequest = serviceRequestRepository.findById(request.getServiceRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + request.getServiceRequestId()));

        if (serviceRequest.getStatus() != ServiceRequestStatus.OPEN) {
            throw new IllegalArgumentException("Offer can be sent only for OPEN service requests");
        }

        User provider = userRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + request.getProviderId()));

        if (provider.getRole() != UserRole.PROVIDER) {
            throw new IllegalArgumentException("Only PROVIDER users can send offers");
        }

        ProviderProfile providerProfile = providerProfileRepository.findByUserId(provider.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider profile not found for provider id: " + provider.getId()));

        if (!Boolean.TRUE.equals(providerProfile.getVerified())) {
            throw new IllegalArgumentException("Only verified providers can send offers");
        }

        if (!providerProfile.getCategory().getId().equals(serviceRequest.getCategory().getId())) {
            throw new IllegalArgumentException("Provider can send offer only for matching service category");
        }

        if (!providerProfile.getCity().equalsIgnoreCase(serviceRequest.getCity())) {
            throw new IllegalArgumentException("Provider can send offer only for service requests in the same city");
        }

        if (serviceOfferRepository.existsByServiceRequestIdAndProviderId(
                serviceRequest.getId(),
                provider.getId())) {
            throw new IllegalArgumentException("Provider has already sent an offer for this service request");
        }

        ServiceOffer serviceOffer = new ServiceOffer();
        serviceOffer.setServiceRequest(serviceRequest);
        serviceOffer.setProvider(provider);
        serviceOffer.setPrice(request.getPrice());
        serviceOffer.setMessage(request.getMessage());
        serviceOffer.setStatus(ServiceOfferStatus.PENDING);

        ServiceOffer savedOffer = serviceOfferRepository.save(serviceOffer);

        return convertToResponse(savedOffer);
    }

    public List<ServiceOfferResponse> getAllServiceOffers() {
        return serviceOfferRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ServiceOfferResponse getServiceOfferById(Long id) {
        ServiceOffer serviceOffer = serviceOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service offer not found with id: " + id));

        return convertToResponse(serviceOffer);
    }

    public List<ServiceOfferResponse> getServiceOffersByServiceRequest(Long serviceRequestId) {

        if (!serviceRequestRepository.existsById(serviceRequestId)) {
            throw new ResourceNotFoundException("Service request not found with id: " + serviceRequestId);
        }

        return serviceOfferRepository.findByServiceRequestId(serviceRequestId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ServiceOfferResponse> getServiceOffersByProvider(Long providerId) {

        if (!userRepository.existsById(providerId)) {
            throw new ResourceNotFoundException("Provider not found with id: " + providerId);
        }

        return serviceOfferRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ServiceOfferResponse convertToResponse(ServiceOffer serviceOffer) {
        return new ServiceOfferResponse(
                serviceOffer.getId(),
                serviceOffer.getServiceRequest().getId(),
                serviceOffer.getServiceRequest().getTitle(),
                serviceOffer.getProvider().getId(),
                serviceOffer.getProvider().getName(),
                serviceOffer.getPrice(),
                serviceOffer.getMessage(),
                serviceOffer.getStatus(),
                serviceOffer.getCreatedAt()
        );
    }
}