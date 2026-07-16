package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.AdminDashboardResponse;
import com.shravani.localservicefinder.entity.BookingStatus;
import com.shravani.localservicefinder.entity.ServiceRequestStatus;
import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.entity.UserRole;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.BookingRepository;
import com.shravani.localservicefinder.repository.CategoryRepository;
import com.shravani.localservicefinder.repository.ProviderProfileRepository;
import com.shravani.localservicefinder.repository.ReviewRepository;
import com.shravani.localservicefinder.repository.ServiceRequestRepository;
import com.shravani.localservicefinder.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;

    public AdminService(UserRepository userRepository,
                        ProviderProfileRepository providerProfileRepository,
                        ServiceRequestRepository serviceRequestRepository,
                        BookingRepository bookingRepository,
                        ReviewRepository reviewRepository,
                        CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
    }

    public AdminDashboardResponse getDashboardSummary(Long adminId) {

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only ADMIN can view dashboard summary");
        }

        long totalUsers = userRepository.count();
        long totalCustomers = userRepository.countByRole(UserRole.USER);
        long totalProviders = userRepository.countByRole(UserRole.PROVIDER);
        long totalAdmins = userRepository.countByRole(UserRole.ADMIN);

        long totalProviderProfiles = providerProfileRepository.count();
        long verifiedProviders = providerProfileRepository.countByVerified(true);
        long unverifiedProviders = providerProfileRepository.countByVerified(false);

        long totalServiceRequests = serviceRequestRepository.count();
        long openServiceRequests = serviceRequestRepository.countByStatus(ServiceRequestStatus.OPEN);
        long bookedServiceRequests = serviceRequestRepository.countByStatus(ServiceRequestStatus.BOOKED);
        long completedServiceRequests = serviceRequestRepository.countByStatus(ServiceRequestStatus.COMPLETED);
        long cancelledServiceRequests = serviceRequestRepository.countByStatus(ServiceRequestStatus.CANCELLED);

        long totalBookings = bookingRepository.count();
        long completedBookings = bookingRepository.countByStatus(BookingStatus.COMPLETED);
        long cancelledBookings = bookingRepository.countByStatus(BookingStatus.CANCELLED);

        long totalReviews = reviewRepository.count();
        long totalCategories = categoryRepository.count();

        return new AdminDashboardResponse(
                totalUsers,
                totalCustomers,
                totalProviders,
                totalAdmins,
                totalProviderProfiles,
                verifiedProviders,
                unverifiedProviders,
                totalServiceRequests,
                openServiceRequests,
                bookedServiceRequests,
                completedServiceRequests,
                cancelledServiceRequests,
                totalBookings,
                completedBookings,
                cancelledBookings,
                totalReviews,
                totalCategories
        );
    }
}