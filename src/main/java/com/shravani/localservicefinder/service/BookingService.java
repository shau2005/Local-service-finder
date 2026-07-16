package com.shravani.localservicefinder.service;

import com.shravani.localservicefinder.dto.BookingRequest;
import com.shravani.localservicefinder.dto.BookingResponse;
import com.shravani.localservicefinder.dto.BookingStatusUpdateRequest;
import com.shravani.localservicefinder.entity.Booking;
import com.shravani.localservicefinder.entity.BookingStatus;
import com.shravani.localservicefinder.entity.ServiceOffer;
import com.shravani.localservicefinder.entity.ServiceOfferStatus;
import com.shravani.localservicefinder.entity.ServiceRequest;
import com.shravani.localservicefinder.entity.ServiceRequestStatus;
import com.shravani.localservicefinder.entity.User;
import com.shravani.localservicefinder.exception.ResourceNotFoundException;
import com.shravani.localservicefinder.repository.BookingRepository;
import com.shravani.localservicefinder.repository.ServiceOfferRepository;
import com.shravani.localservicefinder.repository.ServiceRequestRepository;
import com.shravani.localservicefinder.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ServiceOfferRepository serviceOfferRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          ServiceOfferRepository serviceOfferRepository,
                          ServiceRequestRepository serviceRequestRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.serviceOfferRepository = serviceOfferRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        ServiceOffer selectedOffer = serviceOfferRepository.findById(request.getServiceOfferId())
                .orElseThrow(() -> new ResourceNotFoundException("Service offer not found with id: " + request.getServiceOfferId()));

        if (selectedOffer.getStatus() != ServiceOfferStatus.PENDING) {
            throw new IllegalArgumentException("Only PENDING offers can be accepted");
        }

        ServiceRequest serviceRequest = selectedOffer.getServiceRequest();

        if (serviceRequest.getStatus() != ServiceRequestStatus.OPEN) {
            throw new IllegalArgumentException("Booking can be created only for OPEN service requests");
        }

        if (!serviceRequest.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Only the user who created the service request can accept an offer");
        }

        if (bookingRepository.existsByServiceRequestId(serviceRequest.getId())) {
            throw new IllegalArgumentException("Booking already exists for this service request");
        }

        if (bookingRepository.existsByServiceOfferId(selectedOffer.getId())) {
            throw new IllegalArgumentException("Booking already exists for this service offer");
        }

        Booking booking = new Booking();
        booking.setServiceRequest(serviceRequest);
        booking.setServiceOffer(selectedOffer);
        booking.setUser(serviceRequest.getUser());
        booking.setProvider(selectedOffer.getProvider());
        booking.setScheduledDate(serviceRequest.getPreferredDate());
        booking.setScheduledTime(serviceRequest.getPreferredTime());
        booking.setStatus(BookingStatus.CONFIRMED);

        selectedOffer.setStatus(ServiceOfferStatus.ACCEPTED);

        List<ServiceOffer> allOffersForRequest =
                serviceOfferRepository.findByServiceRequestId(serviceRequest.getId());

        for (ServiceOffer offer : allOffersForRequest) {
            if (!offer.getId().equals(selectedOffer.getId())) {
                offer.setStatus(ServiceOfferStatus.REJECTED);
            }
        }

        serviceRequest.setStatus(ServiceRequestStatus.BOOKED);

        Booking savedBooking = bookingRepository.save(booking);
        serviceOfferRepository.saveAll(allOffersForRequest);
        serviceRequestRepository.save(serviceRequest);

        return convertToResponse(savedBooking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        return convertToResponse(booking);
    }

    public List<BookingResponse> getBookingsByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<BookingResponse> getBookingsByProvider(Long providerId) {

        if (!userRepository.existsById(providerId)) {
            throw new ResourceNotFoundException("Provider not found with id: " + providerId);
        }

        return bookingRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional
    public BookingResponse updateBookingStatus(Long bookingId, BookingStatusUpdateRequest request) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only CONFIRMED bookings can be updated");
        }

        if (request.getStatus() != BookingStatus.COMPLETED && request.getStatus() != BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking status can be updated only to COMPLETED or CANCELLED");
        }

        booking.setStatus(request.getStatus());

        ServiceRequest serviceRequest = booking.getServiceRequest();

        if (request.getStatus() == BookingStatus.COMPLETED) {
            serviceRequest.setStatus(ServiceRequestStatus.COMPLETED);
        }

        if (request.getStatus() == BookingStatus.CANCELLED) {
            serviceRequest.setStatus(ServiceRequestStatus.CANCELLED);
        }

        Booking updatedBooking = bookingRepository.save(booking);
        serviceRequestRepository.save(serviceRequest);

        return convertToResponse(updatedBooking);
    }

    private BookingResponse convertToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getServiceRequest().getId(),
                booking.getServiceRequest().getTitle(),
                booking.getServiceOffer().getId(),
                booking.getUser().getId(),
                booking.getUser().getName(),
                booking.getProvider().getId(),
                booking.getProvider().getName(),
                booking.getServiceOffer().getPrice(),
                booking.getScheduledDate(),
                booking.getScheduledTime(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }
}