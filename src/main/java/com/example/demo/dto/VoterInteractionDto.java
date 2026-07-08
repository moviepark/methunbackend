package com.example.demo.dto;

import com.example.demo.model.VoterInteraction;
import java.time.LocalDateTime;

public class VoterInteractionDto {
    private Long interactionId;
    private Long voterId;
    private String voterName;
    private Long volunteerId;
    private String volunteerName;
    private VoterInteraction.InteractionChannel interactionChannel;
    private VoterInteraction.SentimentDetected sentimentDetected;
    private String policyIssueTag;
    private String interactionNotes;
    private LocalDateTime loggedAt;

    public Long getInteractionId() { return interactionId; }
    public void setInteractionId(Long interactionId) { this.interactionId = interactionId; }
    public Long getVoterId() { return voterId; }
    public void setVoterId(Long voterId) { this.voterId = voterId; }
    public String getVoterName() { return voterName; }
    public void setVoterName(String voterName) { this.voterName = voterName; }
    public Long getVolunteerId() { return volunteerId; }
    public void setVolunteerId(Long volunteerId) { this.volunteerId = volunteerId; }
    public String getVolunteerName() { return volunteerName; }
    public void setVolunteerName(String volunteerName) { this.volunteerName = volunteerName; }
    public VoterInteraction.InteractionChannel getInteractionChannel() { return interactionChannel; }
    public void setInteractionChannel(VoterInteraction.InteractionChannel interactionChannel) { this.interactionChannel = interactionChannel; }
    public VoterInteraction.SentimentDetected getSentimentDetected() { return sentimentDetected; }
    public void setSentimentDetected(VoterInteraction.SentimentDetected sentimentDetected) { this.sentimentDetected = sentimentDetected; }
    public String getPolicyIssueTag() { return policyIssueTag; }
    public void setPolicyIssueTag(String policyIssueTag) { this.policyIssueTag = policyIssueTag; }
    public String getInteractionNotes() { return interactionNotes; }
    public void setInteractionNotes(String interactionNotes) { this.interactionNotes = interactionNotes; }
    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}
