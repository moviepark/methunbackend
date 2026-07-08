package com.example.demo.service;

import com.example.demo.dto.CampaignAccountDto;
import com.example.demo.model.CampaignAccount;
import com.example.demo.repository.CampaignAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignAccountService {
    private final CampaignAccountRepository repository;

    public CampaignAccountService(CampaignAccountRepository repository) {
        this.repository = repository;
    }

    public List<CampaignAccountDto> getAllStaff() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public CampaignAccountDto toDto(CampaignAccount account) {
        CampaignAccountDto dto = new CampaignAccountDto();
        dto.setAccountId(account.getAccountId());
        dto.setEmail(account.getEmail());
        dto.setFullName(account.getFullName());
        dto.setDomainRole(account.getDomainRole());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }
}
