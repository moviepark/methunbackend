package com.example.demo.controller;

import com.example.demo.dto.VoterInteractionDto;
import com.example.demo.service.InteractionProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class VoterInteractionController {
    private final InteractionProcessingService interactionProcessingService;

    public VoterInteractionController(InteractionProcessingService interactionProcessingService) {
        this.interactionProcessingService = interactionProcessingService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER', 'COMPLIANCE_AUDITOR')")
    public ResponseEntity<List<VoterInteractionDto>> getInteractions() {
        return ResponseEntity.ok(interactionProcessingService.getAllInteractions());
    }

    @PostMapping("/log")
    @PreAuthorize("hasAuthority('OUTREACH_VOLUNTEER')")
    public ResponseEntity<VoterInteractionDto> logInteraction(@RequestBody VoterInteractionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interactionProcessingService.logInteractionSession(dto));
    }

    @GetMapping("/recent-feed")
    @PreAuthorize("hasAnyAuthority('CAMPAIGN_DIRECTOR', 'FIELD_ORGANIZER', 'COMPLIANCE_AUDITOR')")
    public ResponseEntity<List<VoterInteractionDto>> recentFeed() {
        return ResponseEntity.ok(interactionProcessingService.recentFeed());
    }
}
