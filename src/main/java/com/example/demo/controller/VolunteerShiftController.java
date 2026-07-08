package com.example.demo.controller;

import com.example.demo.dto.VolunteerShiftDto;
import com.example.demo.model.VolunteerShift;
import com.example.demo.service.VolunteerOperationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shifts")
public class VolunteerShiftController {
    private final VolunteerOperationsService volunteerOperationsService;

    public VolunteerShiftController(VolunteerOperationsService volunteerOperationsService) {
        this.volunteerOperationsService = volunteerOperationsService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER', 'OUTREACH_VOLUNTEER', 'COMPLIANCE_AUDITOR')")
    public ResponseEntity<List<VolunteerShiftDto>> getShifts() {
        return ResponseEntity.ok(volunteerOperationsService.getAllShifts());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<VolunteerShiftDto> createShift(@RequestBody VolunteerShiftDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(volunteerOperationsService.createShift(dto));
    }

    @PostMapping("/{id}/reserve")
    @PreAuthorize("hasAuthority('OUTREACH_VOLUNTEER')")
    public ResponseEntity<VolunteerShiftDto> reserve(@PathVariable Long id, @RequestParam Long accountId) {
        return ResponseEntity.ok(volunteerOperationsService.reserveShiftSlot(id, accountId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<VolunteerShiftDto> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        VolunteerShift.ShiftStatus status = VolunteerShift.ShiftStatus.valueOf(body.get("shiftStatus"));
        return ResponseEntity.ok(volunteerOperationsService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAMPAIGN_DIRECTOR')")
    public ResponseEntity<String> deleteShift(@PathVariable Long id) {
        volunteerOperationsService.deleteShift(id);
        return ResponseEntity.ok("VolunteerShift deleted successfully.");
    }

    @GetMapping("/my-schedule")
    @PreAuthorize("hasAuthority('OUTREACH_VOLUNTEER')")
    public ResponseEntity<List<VolunteerShiftDto>> mySchedule(@RequestParam Long accountId) {
        return ResponseEntity.ok(volunteerOperationsService.getMySchedule(accountId));
    }
}
