package com.example.demo.controller;

import com.example.demo.dto.CampaignAccountDto;
import com.example.demo.service.CampaignAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class CampaignAccountController {
    private final CampaignAccountService campaignAccountService;

    public CampaignAccountController(CampaignAccountService campaignAccountService) {
        this.campaignAccountService = campaignAccountService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CAMPAIGN_DIRECTOR')")
    public ResponseEntity<List<CampaignAccountDto>> getStaff() {
        return ResponseEntity.ok(campaignAccountService.getAllStaff());
    }
}
