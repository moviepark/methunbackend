package com.example.demo.controller;

import com.example.demo.dto.DistrictMetricsDto;
import com.example.demo.dto.VoterProfileDto;
import com.example.demo.service.VoterEngagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterProfileController {
    private final VoterEngagementService voterEngagementService;

    public VoterProfileController(VoterEngagementService voterEngagementService) {
        this.voterEngagementService = voterEngagementService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER', 'OUTREACH_VOLUNTEER')")
    public ResponseEntity<List<VoterProfileDto>> getProfiles() {
        return ResponseEntity.ok(voterEngagementService.getProfiles());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<VoterProfileDto> createProfile(@RequestBody VoterProfileDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voterEngagementService.createProfile(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<VoterProfileDto> updateProfile(@PathVariable Long id, @RequestBody VoterProfileDto dto) {
        return ResponseEntity.ok(voterEngagementService.updateProfile(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAMPAIGN_DIRECTOR')")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        voterEngagementService.deleteProfile(id);
        return ResponseEntity.ok("VoterProfile deleted successfully.");
    }

    @GetMapping("/district-metrics")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<DistrictMetricsDto> districtMetrics(@RequestParam String district) {
        return ResponseEntity.ok(voterEngagementService.calculateDistrictSupportMetrics(district));
    }
}
