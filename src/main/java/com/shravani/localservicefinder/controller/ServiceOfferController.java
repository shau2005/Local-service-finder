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
    public ResponseEntity<ServiceOfferResponse> createServiceOffer(@Valid @RequestBody ServiceOfferRequest request) {
        ServiceOfferResponse savedOffer = serviceOfferService.createServiceOffer(request);
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceOfferResponse>> getAllOffers() {
        List<ServiceOfferResponse> offers = serviceOfferService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOfferResponse> getOfferById(@PathVariable Long id) {
        ServiceOfferResponse offer = serviceOfferService.getOfferById(id);
        return ResponseEntity.ok(offer);
    }

    @GetMapping("/request/{serviceRequestId}")
    public ResponseEntity<List<ServiceOfferResponse>> getOffersByServiceRequest(@PathVariable Long serviceRequestId) {
        List<ServiceOfferResponse> offers = serviceOfferService.getOffersByServiceRequest(serviceRequestId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServiceOfferResponse>> getOffersByProvider(@PathVariable Long providerId) {
        List<ServiceOfferResponse> offers = serviceOfferService.getOffersByProvider(providerId);
        return ResponseEntity.ok(offers);
    }
}
