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

    public ServiceOfferService(
            ServiceOfferRepository serviceOfferRepository,
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

        if (!serviceRequest.getCategory().isActive()) {
            throw new RuntimeException("Cannot send offer because category is inactive: "
                    + serviceRequest.getCategory().getName());
        }

        if (serviceRequest.getStatus() != ServiceRequestStatus.OPEN) {
            throw new RuntimeException("Offer can be sent only for OPEN service requests");
        }

        User provider = userRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + request.getProviderId()));

        if (provider.getRole() != UserRole.PROVIDER) {
            throw new RuntimeException("Only PROVIDER can send service offer");
        }

        ProviderProfile providerProfile = providerProfileRepository.findByUserId(provider.getId())
                .orElseThrow(() -> new RuntimeException("Provider profile not found for provider id: " + provider.getId()));

        if (!providerProfile.getVerified()) {
            throw new RuntimeException("Provider is not verified yet");
        }

        if (!providerProfile.getCategory().getId().equals(serviceRequest.getCategory().getId())) {
            throw new RuntimeException("Provider category does not match service request category");
        }

        if (!providerProfile.getCity().equalsIgnoreCase(serviceRequest.getCity())) {
            throw new RuntimeException("Provider city does not match service request city");
        }

        if (serviceOfferRepository.existsByServiceRequestIdAndProviderId(
                serviceRequest.getId(),
                provider.getId())) {
            throw new RuntimeException("Provider has already sent an offer for this service request");
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

    public List<ServiceOfferResponse> getAllServiceOffers() {
        return serviceOfferRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ServiceOfferResponse getServiceOfferById(Long id) {
        ServiceOffer offer = serviceOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service offer not found with id: " + id));

        return convertToResponse(offer);
    }

    public List<ServiceOfferResponse> getServiceOffersByServiceRequest(Long serviceRequestId) {
        return serviceOfferRepository.findByServiceRequestId(serviceRequestId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ServiceOfferResponse> getServiceOffersByProvider(Long providerId) {
        return serviceOfferRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ServiceOfferResponse convertToResponse(ServiceOffer offer) {
        ServiceOfferResponse response = new ServiceOfferResponse();

        response.setId(offer.getId());
        response.setServiceRequestId(offer.getServiceRequest().getId());
        response.setRequestTitle(offer.getServiceRequest().getTitle());
        response.setProviderId(offer.getProvider().getId());
        response.setProviderName(offer.getProvider().getName());
        response.setPrice(offer.getPrice());
        response.setMessage(offer.getMessage());
        response.setStatus(offer.getStatus());
        response.setCreatedAt(offer.getCreatedAt());

        return response;
    }
}