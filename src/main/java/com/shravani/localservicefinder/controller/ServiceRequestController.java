package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.ServiceRequestRequest;
import com.shravani.localservicefinder.dto.ServiceRequestResponse;
import com.shravani.localservicefinder.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponse> createServiceRequest(
            @Valid @RequestBody ServiceRequestRequest request) {

        return ResponseEntity.ok(serviceRequestService.createServiceRequest(request));
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestResponse>> getAllServiceRequests() {
        return ResponseEntity.ok(serviceRequestService.getAllServiceRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestResponse> getServiceRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRequestService.getServiceRequestById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ServiceRequestResponse>> getServiceRequestsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(serviceRequestService.getServiceRequestsByUser(userId));
    }

    @GetMapping("/open")
    public ResponseEntity<List<ServiceRequestResponse>> getOpenServiceRequests() {
        return ResponseEntity.ok(serviceRequestService.getOpenServiceRequests());
    }

    @GetMapping("/open/category/{categoryId}/city/{city}")
    public ResponseEntity<List<ServiceRequestResponse>> getOpenRequestsByCategoryAndCity(
            @PathVariable Long categoryId,
            @PathVariable String city) {

        return ResponseEntity.ok(serviceRequestService.getOpenRequestsByCategoryAndCity(categoryId, city));
    }
}