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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Service request not found with id: " + request.getServiceRequestId()
                ));

        if (serviceRequest.getStatus() != ServiceRequestStatus.OPEN) {
            throw new IllegalArgumentException("Offer can be sent only for OPEN service requests");
        }

        User provider = userRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Provider not found with id: " + request.getProviderId()
                ));

        if (provider.getRole() != UserRole.PROVIDER) {
            throw new IllegalArgumentException("Only users with PROVIDER role can send offers");
        }

        ProviderProfile providerProfile = providerProfileRepository.findByUserId(request.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Provider profile not found for user id: " + request.getProviderId()
                ));

        if (!providerProfile.getCategory().getId().equals(serviceRequest.getCategory().getId())) {
            throw new IllegalArgumentException("Provider category does not match service request category");
        }

        if (!providerProfile.getCity().equalsIgnoreCase(serviceRequest.getCity())) {
            throw new IllegalArgumentException("Provider city does not match service request city");
        }

        if (serviceOfferRepository.existsByServiceRequestIdAndProviderId(
                request.getServiceRequestId(),
                request.getProviderId())) {
            throw new IllegalArgumentException("Provider has already sent an offer for this service request");
        }

        ServiceOffer offer = new ServiceOffer();
        offer.setServiceRequest(serviceRequest);
        offer.setProvider(provider);
        offer.setPrice(request.getPrice());
        offer.setMessage(request.getMessage());
        offer.setStatus(ServiceOfferStatus.PENDING);

        ServiceOffer savedOffer = serviceOfferRepository.save(offer);

        return convertToResponse(savedOffer);
    }

    public List<ServiceOfferResponse> getAllOffers() {
        return serviceOfferRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ServiceOfferResponse getOfferById(Long id) {
        ServiceOffer offer = serviceOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service offer not found with id: " + id));

        return convertToResponse(offer);
    }

    public List<ServiceOfferResponse> getOffersByServiceRequest(Long serviceRequestId) {

        if (!serviceRequestRepository.existsById(serviceRequestId)) {
            throw new ResourceNotFoundException("Service request not found with id: " + serviceRequestId);
        }

        return serviceOfferRepository.findByServiceRequestId(serviceRequestId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ServiceOfferResponse> getOffersByProvider(Long providerId) {

        if (!userRepository.existsById(providerId)) {
            throw new ResourceNotFoundException("Provider not found with id: " + providerId);
        }

        return serviceOfferRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ServiceOfferResponse convertToResponse(ServiceOffer offer) {
        return new ServiceOfferResponse(
                offer.getId(),
                offer.getServiceRequest().getId(),
                offer.getServiceRequest().getTitle(),
                offer.getProvider().getId(),
                offer.getProvider().getName(),
                offer.getPrice(),
                offer.getMessage(),
                offer.getStatus(),
                offer.getCreatedAt()
        );
    }
}
