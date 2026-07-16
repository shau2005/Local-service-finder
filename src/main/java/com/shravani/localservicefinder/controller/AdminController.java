package com.shravani.localservicefinder.controller;

import com.shravani.localservicefinder.dto.AdminDashboardResponse;
import com.shravani.localservicefinder.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard/{adminId}")
    public ResponseEntity<AdminDashboardResponse> getDashboardSummary(@PathVariable Long adminId) {
        AdminDashboardResponse dashboard = adminService.getDashboardSummary(adminId);
        return ResponseEntity.ok(dashboard);
    }
}