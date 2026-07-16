package com.shravani.localservicefinder.repository;

import com.shravani.localservicefinder.entity.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceOfferRepository extends JpaRepository<ServiceOffer, Long> {

    List<ServiceOffer> findByServiceRequestId(Long serviceRequestId);

    List<ServiceOffer> findByProviderId(Long providerId);

    boolean existsByServiceRequestIdAndProviderId(Long serviceRequestId, Long providerId);
}
