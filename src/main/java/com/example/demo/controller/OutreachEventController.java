package com.example.demo.controller;

import com.example.demo.dto.OutreachEventDto;
import com.example.demo.service.OutreachManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class OutreachEventController {
    private final OutreachManagementService outreachManagementService;

    public OutreachEventController(OutreachManagementService outreachManagementService) {
        this.outreachManagementService = outreachManagementService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER', 'OUTREACH_VOLUNTEER', 'COMPLIANCE_AUDITOR')")
    public ResponseEntity<List<OutreachEventDto>> getAllEvents() {
        return ResponseEntity.ok(outreachManagementService.getAllEvents());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER', 'OUTREACH_VOLUNTEER', 'COMPLIANCE_AUDITOR')")
    public ResponseEntity<OutreachEventDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(outreachManagementService.getEventById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<Map<String, Object>> createEvent(@RequestBody OutreachEventDto dto) {
        OutreachEventDto saved = outreachManagementService.publishOutreachEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "OutreachEvent created successfully.",
                "data", saved,
                "status", 201
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<Map<String, Object>> updateEvent(@PathVariable Long id, @RequestBody OutreachEventDto dto) {
        OutreachEventDto updated = outreachManagementService.updateEvent(id, dto);
        return ResponseEntity.ok(Map.of("message", "OutreachEvent updated successfully.", "data", updated, "status", 200));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAMPAIGN_DIRECTOR')")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        outreachManagementService.deleteEvent(id);
        return ResponseEntity.ok("OutreachEvent deleted successfully.");
    }

    @PostMapping("/{id}/conclude")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER')")
    public ResponseEntity<OutreachEventDto> conclude(@PathVariable Long id) {
        return ResponseEntity.ok(outreachManagementService.concludeEventWorkflows(id));
    }
}
