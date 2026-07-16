package com.shravani.localservicefinder.dto;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalCustomers;
    private long totalProviders;
    private long totalAdmins;

    private long totalProviderProfiles;
    private long verifiedProviders;
    private long unverifiedProviders;

    private long totalServiceRequests;
    private long openServiceRequests;
    private long bookedServiceRequests;
    private long completedServiceRequests;
    private long cancelledServiceRequests;

    private long totalBookings;
    private long completedBookings;
    private long cancelledBookings;

    private long totalReviews;
    private long totalCategories;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(long totalUsers,
                                  long totalCustomers,
                                  long totalProviders,
                                  long totalAdmins,
                                  long totalProviderProfiles,
                                  long verifiedProviders,
                                  long unverifiedProviders,
                                  long totalServiceRequests,
                                  long openServiceRequests,
                                  long bookedServiceRequests,
                                  long completedServiceRequests,
                                  long cancelledServiceRequests,
                                  long totalBookings,
                                  long completedBookings,
                                  long cancelledBookings,
                                  long totalReviews,
                                  long totalCategories) {
        this.totalUsers = totalUsers;
        this.totalCustomers = totalCustomers;
        this.totalProviders = totalProviders;
        this.totalAdmins = totalAdmins;
        this.totalProviderProfiles = totalProviderProfiles;
        this.verifiedProviders = verifiedProviders;
        this.unverifiedProviders = unverifiedProviders;
        this.totalServiceRequests = totalServiceRequests;
        this.openServiceRequests = openServiceRequests;
        this.bookedServiceRequests = bookedServiceRequests;
        this.completedServiceRequests = completedServiceRequests;
        this.cancelledServiceRequests = cancelledServiceRequests;
        this.totalBookings = totalBookings;
        this.completedBookings = completedBookings;
        this.cancelledBookings = cancelledBookings;
        this.totalReviews = totalReviews;
        this.totalCategories = totalCategories;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public long getTotalProviders() {
        return totalProviders;
    }

    public long getTotalAdmins() {
        return totalAdmins;
    }

    public long getTotalProviderProfiles() {
        return totalProviderProfiles;
    }

    public long getVerifiedProviders() {
        return verifiedProviders;
    }

    public long getUnverifiedProviders() {
        return unverifiedProviders;
    }

    public long getTotalServiceRequests() {
        return totalServiceRequests;
    }

    public long getOpenServiceRequests() {
        return openServiceRequests;
    }

    public long getBookedServiceRequests() {
        return bookedServiceRequests;
    }

    public long getCompletedServiceRequests() {
        return completedServiceRequests;
    }

    public long getCancelledServiceRequests() {
        return cancelledServiceRequests;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public long getCompletedBookings() {
        return completedBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public void setTotalProviders(long totalProviders) {
        this.totalProviders = totalProviders;
    }

    public void setTotalAdmins(long totalAdmins) {
        this.totalAdmins = totalAdmins;
    }

    public void setTotalProviderProfiles(long totalProviderProfiles) {
        this.totalProviderProfiles = totalProviderProfiles;
    }

    public void setVerifiedProviders(long verifiedProviders) {
        this.verifiedProviders = verifiedProviders;
    }

    public void setUnverifiedProviders(long unverifiedProviders) {
        this.unverifiedProviders = unverifiedProviders;
    }

    public void setTotalServiceRequests(long totalServiceRequests) {
        this.totalServiceRequests = totalServiceRequests;
    }

    public void setOpenServiceRequests(long openServiceRequests) {
        this.openServiceRequests = openServiceRequests;
    }

    public void setBookedServiceRequests(long bookedServiceRequests) {
        this.bookedServiceRequests = bookedServiceRequests;
    }

    public void setCompletedServiceRequests(long completedServiceRequests) {
        this.completedServiceRequests = completedServiceRequests;
    }

    public void setCancelledServiceRequests(long cancelledServiceRequests) {
        this.cancelledServiceRequests = cancelledServiceRequests;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public void setCompletedBookings(long completedBookings) {
        this.completedBookings = completedBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }
}