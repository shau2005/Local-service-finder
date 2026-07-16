package com.shravani.localservicefinder.repository;

import com.shravani.localservicefinder.entity.ServiceRequest;
import com.shravani.localservicefinder.entity.ServiceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByUserId(Long userId);

    List<ServiceRequest> findByCategoryId(Long categoryId);

    List<ServiceRequest> findByStatus(ServiceRequestStatus status);

    List<ServiceRequest> findByCategoryIdAndCityIgnoreCaseAndStatus(
            Long categoryId,
            String city,
            ServiceRequestStatus status
    );

    long countByStatus(ServiceRequestStatus status);
}