package com.example.demo.service;

import com.example.demo.dto.VoterInteractionDto;
import com.example.demo.model.CampaignAccount;
import com.example.demo.model.VoterInteraction;
import com.example.demo.model.VoterProfile;
import com.example.demo.repository.CampaignAccountRepository;
import com.example.demo.repository.VoterInteractionRepository;
import com.example.demo.repository.VoterProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InteractionProcessingService {
    private final VoterInteractionRepository voterInteractionRepository;
    private final VoterProfileRepository voterProfileRepository;
    private final CampaignAccountRepository campaignAccountRepository;
    private final VoterEngagementService voterEngagementService;

    public InteractionProcessingService(VoterInteractionRepository voterInteractionRepository,
                                        VoterProfileRepository voterProfileRepository,
                                        CampaignAccountRepository campaignAccountRepository,
                                        VoterEngagementService voterEngagementService) {
        this.voterInteractionRepository = voterInteractionRepository;
        this.voterProfileRepository = voterProfileRepository;
        this.campaignAccountRepository = campaignAccountRepository;
        this.voterEngagementService = voterEngagementService;
    }

    public List<VoterInteractionDto> getAllInteractions() {
        return voterInteractionRepository.findAll().stream().map(this::toDto).toList();
    }

    public List<VoterInteractionDto> recentFeed() {
        return voterInteractionRepository.findRecentInteractions().stream().map(this::toDto).toList();
    }

    @Transactional
    public VoterInteractionDto logInteractionSession(VoterInteractionDto dto) {
        if (dto.getVoterId() == null) throw new RuntimeException("Voter ID is required");
        if (dto.getVolunteerId() == null) throw new RuntimeException("Volunteer ID is required");
        if (dto.getInteractionChannel() == null) throw new RuntimeException("Interaction channel is required");
        if (dto.getSentimentDetected() == null) throw new RuntimeException("Sentiment detected is required");

        VoterProfile voter = voterProfileRepository.findById(dto.getVoterId())
                .orElseThrow(() -> new RuntimeException("VoterProfile not found"));
        CampaignAccount volunteer = campaignAccountRepository.findById(dto.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer account not found"));

        VoterInteraction interaction = new VoterInteraction();
        interaction.setVoter(voter);
        interaction.setVolunteer(volunteer);
        interaction.setInteractionChannel(dto.getInteractionChannel());
        interaction.setSentimentDetected(dto.getSentimentDetected());
        interaction.setPolicyIssueTag(dto.getPolicyIssueTag());
        interaction.setInteractionNotes(dto.getInteractionNotes());

        VoterInteraction saved = voterInteractionRepository.save(interaction);
        voterEngagementService.recalculateSupportScore(voter.getVoterId(), dto.getSentimentDetected());
        return toDto(saved);
    }

    public VoterInteractionDto toDto(VoterInteraction interaction) {
        VoterInteractionDto dto = new VoterInteractionDto();
        dto.setInteractionId(interaction.getInteractionId());
        if (interaction.getVoter() != null) {
            dto.setVoterId(interaction.getVoter().getVoterId());
            dto.setVoterName(interaction.getVoter().getFirstName() + " " + interaction.getVoter().getLastName());
        }
        if (interaction.getVolunteer() != null) {
            dto.setVolunteerId(interaction.getVolunteer().getAccountId());
            dto.setVolunteerName(interaction.getVolunteer().getFullName());
        }
        dto.setInteractionChannel(interaction.getInteractionChannel());
        dto.setSentimentDetected(interaction.getSentimentDetected());
        dto.setPolicyIssueTag(interaction.getPolicyIssueTag());
        dto.setInteractionNotes(interaction.getInteractionNotes());
        dto.setLoggedAt(interaction.getLoggedAt());
        return dto;
    }
}
