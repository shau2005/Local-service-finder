package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.ServiceOfferRequest;
import com.shravani.localservicefinder.dto.ServiceOfferResponse;
import com.shravani.localservicefinder.service.ServiceOfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-offers")
public class ServiceOfferController {

    private final ServiceOfferService serviceOfferService;

    public ServiceOfferController(ServiceOfferService serviceOfferService) {
        this.serviceOfferService = serviceOfferService;
    }

    @PostMapping
    public ResponseEntity<ServiceOfferResponse> createServiceOffer(
            @Valid @RequestBody ServiceOfferRequest request) {

        ServiceOfferResponse savedOffer = serviceOfferService.createServiceOffer(request);
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceOfferResponse>> getAllServiceOffers() {
        List<ServiceOfferResponse> serviceOffers = serviceOfferService.getAllServiceOffers();
        return ResponseEntity.ok(serviceOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOfferResponse> getServiceOfferById(@PathVariable Long id) {
        ServiceOfferResponse serviceOffer = serviceOfferService.getServiceOfferById(id);
        return ResponseEntity.ok(serviceOffer);
    }

    @GetMapping("/request/{serviceRequestId}")
    public ResponseEntity<List<ServiceOfferResponse>> getServiceOffersByServiceRequest(
            @PathVariable Long serviceRequestId) {

        List<ServiceOfferResponse> serviceOffers =
                serviceOfferService.getServiceOffersByServiceRequest(serviceRequestId);

        return ResponseEntity.ok(serviceOffers);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServiceOfferResponse>> getServiceOffersByProvider(
            @PathVariable Long providerId) {

        List<ServiceOfferResponse> serviceOffers =
                serviceOfferService.getServiceOffersByProvider(providerId);

        return ResponseEntity.ok(serviceOffers);
    }
}