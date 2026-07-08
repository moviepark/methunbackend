package com.example.demo.service;

import com.example.demo.dto.DistrictMetricsDto;
import com.example.demo.dto.VoterProfileDto;
import com.example.demo.model.VoterInteraction;
import com.example.demo.model.VoterProfile;
import com.example.demo.repository.VoterInteractionRepository;
import com.example.demo.repository.VoterProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoterEngagementService {
    private final VoterProfileRepository voterProfileRepository;
    private final VoterInteractionRepository voterInteractionRepository;

    public VoterEngagementService(VoterProfileRepository voterProfileRepository,
                                  VoterInteractionRepository voterInteractionRepository) {
        this.voterProfileRepository = voterProfileRepository;
        this.voterInteractionRepository = voterInteractionRepository;
    }

    public List<VoterProfileDto> getProfiles() {
        return voterProfileRepository.findAll().stream().map(this::toDto).toList();
    }

    public VoterProfileDto getProfile(Long id) {
        return toDto(voterProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("VoterProfile not found")));
    }

    @Transactional
    public VoterProfileDto createProfile(VoterProfileDto dto) {
        validate(dto);
        VoterProfile voter = new VoterProfile();
        applyDto(voter, dto);
        if (dto.getEngagementStatus() != null) voter.setEngagementStatus(dto.getEngagementStatus());
        return toDto(voterProfileRepository.save(voter));
    }

    @Transactional
    public VoterProfileDto updateProfile(Long id, VoterProfileDto dto) {
        VoterProfile voter = voterProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("VoterProfile not found"));
        applyDto(voter, dto);
        if (dto.getEngagementStatus() != null) voter.setEngagementStatus(dto.getEngagementStatus());
        if (dto.getLastContactedDate() != null) voter.setLastContactedDate(dto.getLastContactedDate());
        return toDto(voterProfileRepository.save(voter));
    }

    @Transactional
    public void deleteProfile(Long id) {
        voterProfileRepository.delete(voterProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("VoterProfile not found")));
    }

    public DistrictMetricsDto calculateDistrictSupportMetrics(String district) {
        Double average = voterProfileRepository.getAverageSupportScoreByDistrict(district);
        long count = voterProfileRepository.countByElectoralDistrict(district);
        Map<String, Long> distribution = new HashMap<>();
        for (Object[] row : voterInteractionRepository.countInteractionsBySentiment()) {
            VoterInteraction.SentimentDetected sentiment = (VoterInteraction.SentimentDetected) row[0];
            Long total = (Long) row[1];
            distribution.put(sentiment.name(), total);
        }
        return new DistrictMetricsDto(district, count, average == null ? 0.0 : average, distribution);
    }

    @Transactional
    public void updateVoterStatusLifecycle(Long voterId) {
        VoterProfile voter = voterProfileRepository.findById(voterId).orElseThrow(() -> new RuntimeException("VoterProfile not found"));
        if (voter.getEngagementStatus() == VoterProfile.EngagementStatus.UNCONTACTED) {
            voter.setEngagementStatus(VoterProfile.EngagementStatus.CONTACTED);
        }
        if (voter.getSupportScore() != null && voter.getSupportScore() < 35) {
            voter.setEngagementStatus(VoterProfile.EngagementStatus.FOLLOW_UP_REQUIRED);
        }
        voter.setLastContactedDate(LocalDateTime.now());
        voterProfileRepository.save(voter);
    }

    @Transactional
    public void recalculateSupportScore(Long voterId, VoterInteraction.SentimentDetected sentiment) {
        VoterProfile voter = voterProfileRepository.findById(voterId).orElseThrow(() -> new RuntimeException("VoterProfile not found"));
        switch (sentiment) {
            case STRONG_SUPPORT -> voter.setSupportScore(90);
            case LEANING -> voter.setSupportScore(65);
            case UNDECIDED -> voter.setSupportScore(50);
            case OPPOSED -> voter.setSupportScore(20);
        }
        updateVoterStatusLifecycle(voterId);
        voterProfileRepository.save(voter);
    }

    private void validate(VoterProfileDto dto) {
        if (dto.getFirstName() == null || dto.getFirstName().isBlank()) throw new RuntimeException("First name is required");
        if (dto.getLastName() == null || dto.getLastName().isBlank()) throw new RuntimeException("Last name is required");
        if (dto.getElectoralDistrict() == null || dto.getElectoralDistrict().isBlank()) throw new RuntimeException("Electoral district is required");
        if (dto.getSupportScore() != null && (dto.getSupportScore() < 0 || dto.getSupportScore() > 100)) throw new RuntimeException("Support score must be between 0 and 100");
    }

    private void applyDto(VoterProfile voter, VoterProfileDto dto) {
        if (dto.getFirstName() != null) voter.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) voter.setLastName(dto.getLastName());
        if (dto.getElectoralDistrict() != null) voter.setElectoralDistrict(dto.getElectoralDistrict());
        if (dto.getContactNumber() != null) voter.setContactNumber(dto.getContactNumber());
        if (dto.getSupportScore() != null) voter.setSupportScore(dto.getSupportScore());
    }

    public VoterProfileDto toDto(VoterProfile voter) {
        VoterProfileDto dto = new VoterProfileDto();
        dto.setVoterId(voter.getVoterId());
        dto.setFirstName(voter.getFirstName());
        dto.setLastName(voter.getLastName());
        dto.setElectoralDistrict(voter.getElectoralDistrict());
        dto.setContactNumber(voter.getContactNumber());
        dto.setSupportScore(voter.getSupportScore());
        dto.setEngagementStatus(voter.getEngagementStatus());
        dto.setLastContactedDate(voter.getLastContactedDate());
        return dto;
    }
}
