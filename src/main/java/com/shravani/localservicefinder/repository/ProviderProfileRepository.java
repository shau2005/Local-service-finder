package com.shravani.localservicefinder.repository;

import com.shravani.localservicefinder.entity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {

    boolean existsByUserId(Long userId);

    List<ProviderProfile> findByCategoryId(Long categoryId);

    List<ProviderProfile> findByCityIgnoreCase(String city);
}
