package com.shravani.localservicefinder.repository;

import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    long countByRole(UserRole role);
}