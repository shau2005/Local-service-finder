package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.ServiceRequestRequest;
import com.shravani.localservicefinder.dto.ServiceRequestResponse;
import com.shravani.localservicefinder.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponse> createServiceRequest(@Valid @RequestBody ServiceRequestRequest request) {
        ServiceRequestResponse savedRequest = serviceRequestService.createServiceRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestResponse>> getAllServiceRequests() {
        List<ServiceRequestResponse> requests = serviceRequestService.getAllServiceRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestResponse> getServiceRequestById(@PathVariable Long id) {
        ServiceRequestResponse request = serviceRequestService.getServiceRequestById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ServiceRequestResponse>> getServiceRequestsByUser(@PathVariable Long userId) {
        List<ServiceRequestResponse> requests = serviceRequestService.getServiceRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/open")
    public ResponseEntity<List<ServiceRequestResponse>> getOpenServiceRequests() {
        List<ServiceRequestResponse> requests = serviceRequestService.getOpenServiceRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/open/category/{categoryId}/city/{city}")
    public ResponseEntity<List<ServiceRequestResponse>> getOpenRequestsByCategoryAndCity(
            @PathVariable Long categoryId,
            @PathVariable String city) {

        List<ServiceRequestResponse> requests =
                serviceRequestService.getOpenRequestsByCategoryAndCity(categoryId, city);

        return ResponseEntity.ok(requests);
    }
}
